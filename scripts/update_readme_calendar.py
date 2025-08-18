#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import subprocess
import datetime
import calendar
import os
import re

# ===== 설정 =====
NAMES = ["곽태근", "김호집", "오창은", "김태민", "추창우"]
READ_ME = "README.md"
START_MARK = "<!-- PROGRESS_START -->"
END_MARK = "<!-- PROGRESS_END -->"
TZ_OFFSET = "+0900"  # Asia/Seoul (KST)
WEEK_START = calendar.SUNDAY  # 달력 시작: 일요일
DOT_GREEN  = "🟢"  # 그날 커밋 + 목표 달성
DOT_ORANGE = "🟠"  # 다른날 커밋 + 목표 달성
DOT_YELLOW = "🟡"  # 커밋 있음 + 목표 미달
DOT_RED    = "🔴"  # 비봇 커밋 없음
# ==============

calendar.setfirstweekday(WEEK_START)
BOT_REGEX = re.compile(r"^\d{4}-\d{2}-\d{2}일자 태스크 배정완료, 화이팅!$")

def run(cmd):
    return subprocess.check_output(cmd, shell=True, text=True, encoding="utf-8").strip()

# ---------- 커밋/메시지 판정 ----------

def git_subjects_for_date_and_path(date_str, path):
    """특정 날짜(KST)의 해당 path 커밋 subject 목록"""
    since = f"{date_str} 00:00:00 {TZ_OFFSET}"
    until = f"{date_str} 23:59:59 {TZ_OFFSET}"
    cmd = (
        f'git log --since="{since}" --until="{until}" '
        f'--pretty=%s -- "{path}" || true'
    )
    out = run(cmd)
    if not out:
        return []
    return [line.strip() for line in out.splitlines() if line.strip()]

def latest_nonbot_commit_for_path(path):
    """
    해당 path에 대한 '비봇' 커밋 중 가장 최근 항목의 (날짜, 커밋해시)를 반환.
    없으면 (None, None).
    """
    # 날짜는 KST 로컬 포맷, 해시는 별도로 얻기 위해 %H 추가
    cmd = f'git log --pretty="%H%x09%ad%x09%s" --date=format-local:"%Y-%m-%d" -- "{path}" || true'
    out = run(cmd)
    if not out:
        return None, None
    for line in out.splitlines():
        line = line.strip()
        if not line:
            continue
        parts = line.split("\t", 2)
        if len(parts) != 3:
            continue
        commit_hash, date_str, subject = parts
        subject = subject.strip()
        if BOT_REGEX.match(subject):
            continue
        return date_str, commit_hash
    return None, None

def commit_flag(date_str, name):
    """
    커밋 관점 판정:
      'O' = 그날 비봇 커밋,
      'L' = 다른 날 비봇 커밋,
      'X' = 비봇 커밋 없음
    """
    path = f"{date_str}/{name}"
    subjects_today = git_subjects_for_date_and_path(date_str, path)
    for s in subjects_today:
        if not BOT_REGEX.match(s):
            return "O"
    nonbot_date, _ = latest_nonbot_commit_for_path(path)
    if nonbot_date is not None and nonbot_date != date_str:
        return "L"
    return "X"

# ---------- 스냅샷 & 파일 개수 (재귀) ----------

def commit_at_end_of_date(date_str):
    """해당 날짜(KST 23:59:59)의 리포 스냅샷 커밋 해시 반환(없으면 빈 문자열)"""
    until = f'{date_str} 23:59:59 {TZ_OFFSET}'
    cmd = f'git rev-list -1 --before="{until}" HEAD || true'
    return run(cmd).strip()

def count_files_recursive_at_commit(commit, base_dir):
    """
    특정 커밋에서 base_dir/ 아래의 모든 파일(=blob) 리스트와 .gitkeep 존재 여부 반환.
    - 출력: (파일경로목록(list[str]), has_gitkeep(bool))
    """
    if not commit:
        return [], False
    base = base_dir.rstrip("/") + "/"
    cmd = f'git ls-tree -r --name-only {commit} -- "{base}" || true'
    out = run(cmd)
    if not out:
        return [], False

    files = []
    has_gitkeep = False
    for line in out.splitlines():
        name = line.strip()
        if not name or not name.startswith(base):
            continue
        files.append(name)
        if os.path.basename(name) == ".gitkeep":
            has_gitkeep = True
    return files, has_gitkeep

def snapshot_for_display_and_goal(date_str, name, cf):
    """
    표시/판정에 사용할 '스냅샷 커밋'을 선택하고, 그 스냅샷에서:
      - 표시용 파일 개수(display_n): .gitkeep 제외
      - 목표값(m): .gitkeep 있으면 4, 없으면 3
      - 충족 여부(ok): (표시용이 아니라 실제 전체 파일수 >= m)
    반환: (display_n, m, ok)
    """
    path = f"{date_str}/{name}"

    if cf == "O":
        # 같은 날: 그날 끝 스냅샷
        commit = commit_at_end_of_date(date_str)
    elif cf == "L":
        # 레트로: 최신 비봇 커밋 스냅샷
        _, commit = latest_nonbot_commit_for_path(path)
        if not commit:
            # 안전망: 없으면 그날 스냅샷 시도
            commit = commit_at_end_of_date(date_str)
    else:  # 'X'
        # 비봇 커밋 없으면 그날 스냅샷으로 계산(대개 0)
        commit = commit_at_end_of_date(date_str)

    files, has_gitkeep = count_files_recursive_at_commit(commit, path)
    total_including_gitkeep = len(files)
    # 표시용 개수는 .gitkeep 제외
    display_n = total_including_gitkeep - (1 if any(os.path.basename(f) == ".gitkeep" for f in files) else 0)
    # 목표값 산정
    m = 4 if has_gitkeep else 3
    ok = total_including_gitkeep >= m
    return display_n, m, ok

# ---------- 달력 렌더링 ----------

def find_all_date_dirs():
    """리포 내 YYYY-MM-DD 디렉터리들을 찾아 실제 날짜 리스트 반환."""
    dates = []
    for entry in os.listdir("."):
        if re.fullmatch(r"\d{4}-\d{2}-\d{2}", entry) and os.path.isdir(entry):
            try:
                y, m, d = map(int, entry.split("-"))
                dates.append(datetime.date(y, m, d))
            except ValueError:
                pass
    return sorted(dates)

def month_iter(start_date, end_date):
    """start_date의 1일 ~ end_date의 1일까지 월 단위 이터레이션."""
    y, m = start_date.year, start_date.month
    while (y < end_date.year) or (y == end_date.year and m <= end_date.month):
        yield y, m
        if m == 12:
            y += 1
            m = 1
        else:
            m += 1

def build_month_calendar(year, month, today_kst):
    cal = calendar.monthcalendar(year, month)
    header_days = ["일", "월", "화", "수", "목", "금", "토"]
    header_days = header_days[-calendar.firstweekday():] + header_days[:-calendar.firstweekday()]

    rows_html = []
    for week in cal:
        tds = []
        for d in week:
            if d == 0:
                tds.append("<td></td>")
                continue

            date_obj = datetime.date(year, month, d)
            if date_obj >= today_kst:
                # 오늘/미래 날짜는 빈 칸
                tds.append(
                    f'<td align="center" valign="top">'
                    f'<div align="right"><sub>{d}</sub></div>'
                    f"</td>"
                )
                continue

            date_str = date_obj.isoformat()
            lines = []
            for name in NAMES:
                cf = commit_flag(date_str, name)  # 'O','L','X'
                display_n, m, ok = snapshot_for_display_and_goal(date_str, name, cf)

                if cf == "O":
                    dot = DOT_GREEN if ok else DOT_YELLOW
                elif cf == "L":
                    dot = DOT_ORANGE if ok else DOT_YELLOW
                else:
                    dot = DOT_RED
                    # cf == 'X'일 땐 ok 의미가 없지만, 표시 일관성을 위해 (n/m) 그대로 둠.

                # 👉 표시를 심플하게: "이름: 🟡 (n/m)"
                lines.append(
                    f"<div style='font-size:13px'>{name}: {dot} "
                    f"(<code>{display_n}/{m}</code>)</div>"
                )

            cell_html = (
                '<td align="center" valign="top" style="min-width:150px">'
                f'<div align="right"><sub>{d}</sub></div>'
                + "".join(lines) +
                "</td>"
            )
            tds.append(cell_html)
        rows_html.append("<tr>" + "".join(tds) + "</tr>")

    month_title = f"### {year}-{month:02d} 코딩테스트 달력 (KST)"
    legend = (
        "<sub>"
        "🟢=그날 커밋+목표달성, "
        "🟠=다른날 커밋+목표달성, "
        "🟡=커밋있음+목표미달, "
        "🔴=커밋없음 · "
        "(표시 n은 .gitkeep 제외)</sub>"
    )
    table_html = (
        f"{month_title}\n\n"
        + legend + "\n\n"
        + '<table>'
        + "<thead><tr>" + "".join([f"<th>{d}</th>" for d in header_days]) + "</tr></thead>"
        + "<tbody>" + "".join(rows_html) + "</tbody>"
        + "</table>\n"
    )
    return table_html

def build_all_months(today_kst):
    date_dirs = find_all_date_dirs()
    if date_dirs:
        start = datetime.date(date_dirs[0].year, date_dirs[0].month, 1)
    else:
        start = datetime.date(today_kst.year, today_kst.month, 1)

    end = datetime.date(today_kst.year, today_kst.month, 1)
    blocks = []
    for y, m in month_iter(start, end):
        block = build_month_calendar(y, m, today_kst)
        is_current = (y == today_kst.year and m == today_kst.month)
        summary = f"{y}-{m:02d}"
        details_open = " open" if is_current else ""
        blocks.append(f"<details{details_open}><summary><strong>{summary}</strong></summary>\n\n{block}\n</details>")
    return "\n\n".join(blocks)

def replace_block(original, new_block):
    if START_MARK in original and END_MARK in original:
        pattern = re.compile(
            rf"{re.escape(START_MARK)}.*?{re.escape(END_MARK)}",
            re.DOTALL
        )
        return pattern.sub(
            f"{START_MARK}\n{new_block}\n{END_MARK}", original
        )
    else:
        return original.rstrip() + "\n\n" + START_MARK + "\n" + new_block + "\n" + END_MARK + "\n"

def main():
    now = datetime.datetime.now(datetime.timezone(datetime.timedelta(hours=9)))
    today_kst = now.date()

    new_block = build_all_months(today_kst)

    if os.path.exists(READ_ME):
        with open(READ_ME, "r", encoding="utf-8") as f:
            content = f.read()
    else:
        content = "# 코딩테스트 연습\n"

    updated = replace_block(content, new_block)

    if updated != content:
        with open(READ_ME, "w", encoding="utf-8") as f:
            f.write(updated)

if __name__ == "__main__":
    main()
