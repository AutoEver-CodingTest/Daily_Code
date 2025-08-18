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

def latest_nonbot_commit_date_for_path(path):
    """
    해당 path에 대한 '비봇' 커밋 중 가장 최근 커밋의 날짜(YYYY-MM-DD, KST 기준)를 반환.
    없으면 None.
    """
    cmd = f'git log --pretty="%ad%x09%s" --date=format-local:"%Y-%m-%d" -- "{path}" || true'
    out = run(cmd)
    if not out:
        return None
    for line in out.splitlines():
        line = line.strip()
        if not line:
            continue
        if "\t" in line:
            date_part, subject = line.split("\t", 1)
        else:
            parts = line.split(" ", 1)
            if len(parts) != 2:
                continue
            date_part, subject = parts
        subject = subject.strip()
        if BOT_REGEX.match(subject):
            continue
        return date_part
    return None

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
    nonbot_any_date = latest_nonbot_commit_date_for_path(path)
    if nonbot_any_date is not None and nonbot_any_date != date_str:
        return "L"
    return "X"

# ---------- 파일 개수(그 날짜 스냅샷) ----------

def commit_at_end_of_date(date_str):
    """해당 날짜(KST 23:59:59)의 리포 스냅샷 커밋 해시 반환(없으면 빈 문자열)"""
    until = f'{date_str} 23:59:59 {TZ_OFFSET}'
    cmd = f'git rev-list -1 --before="{until}" HEAD || true'
    return run(cmd).strip()

def file_count_in_path_at_commit(commit, path):
    """
    특정 커밋에서 path/ 디렉터리 '바로 아래' 파일(=blob) 개수와 .gitkeep 포함 여부 반환.
    재귀로 전체 파일을 받은 뒤, base 바로 아래만 필터링한다.
    """
    if not commit:
        return 0, False

    base = path.rstrip("/") + "/"
    # 재귀로 모든 파일 경로를 받고, base 바로 아래만 카운트
    cmd = f'git ls-tree -r --name-only {commit} -- "{base}" || true'
    out = run(cmd)
    if not out:
        return 0, False

    count = 0
    has_gitkeep = False
    for line in out.splitlines():
        name = line.strip()
        if not name.startswith(base):
            continue
        rest = name[len(base):]  # base 이후
        if "/" in rest:
            # 하위 디렉터리 내부는 제외 (바로 아래만 카운트)
            continue
        count += 1
        if rest == ".gitkeep":
            has_gitkeep = True
    return count, has_gitkeep

def file_req_and_status(date_str, name):
    """
    (파일개수, 목표개수, 충족여부) 반환.
    목표: .gitkeep 있으면 4, 없으면 3 (해당 날짜 23:59:59 KST 스냅샷 기준)
    """
    commit = commit_at_end_of_date(date_str)
    path = f"{date_str}/{name}"
    cnt, has_gitkeep = file_count_in_path_at_commit(commit, path)
    required = 4 if has_gitkeep else 3
    ok = cnt >= required
    return cnt, required, ok

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
                # 커밋/파일 판정
                cf = commit_flag(date_str, name)  # 'O','L','X'
                cnt, req, ok = file_req_and_status(date_str, name)

                # 색상 결정
                if cf == "O":
                    dot = DOT_GREEN if ok else DOT_YELLOW
                elif cf == "L":
                    dot = DOT_ORANGE if ok else DOT_YELLOW
                else:
                    dot = DOT_RED

                pass_icon = "✅" if ok else "❌"
                lines.append(
                    f"<div style='font-size:13px'>{name}: {dot} "
                    f"<span style='font-size:12px'>(<code>{cnt}/{req}</code> {pass_icon})</span></div>"
                )

            cell_html = (
                '<td align="center" valign="top" style="min-width:170px">'
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
        "<code>n/m</code>=파일개수/목표(.gitkeep 있으면 m=4, 없으면 m=3)"
        "</sub>"
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
