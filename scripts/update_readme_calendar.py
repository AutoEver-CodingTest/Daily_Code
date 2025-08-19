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
DOT_GREEN  = "🟢"  # 그날 3커밋 이상 + 누적 3개 이상
DOT_ORANGE = "🟠"  # 누적 3개 이상(레트로 달성 포함), 당일 3커밋 미만
DOT_YELLOW = "🟡"  # 비봇 커밋은 있으나 누적 < 3
DOT_RED    = "🔴"  # 비봇 커밋 없음 (표시 n=0)
GOAL_M = 3        # 항상 3
# ==============

calendar.setfirstweekday(WEEK_START)
BOT_REGEX = re.compile(r"^\d{4}-\d{2}-\d{2}일자 태스크 배정완료, 화이팅!$")

def run(cmd):
    return subprocess.check_output(cmd, shell=True, text=True, encoding="utf-8").strip()

# ---------- 커밋/메시지 조회 ----------
def files_in_nonbot_commits_on_date(date_str, path):
    """해당 날짜에 path에서 비봇 커밋들의 파일 개수를 모두 합산"""
    since = f"{date_str} 00:00:00 {TZ_OFFSET}"
    until = f"{date_str} 23:59:59 {TZ_OFFSET}"
    cmd = f'git log --since="{since}" --until="{until}" --pretty=%H -- "{path}" || true'
    out = run(cmd)
    if not out:
        return 0
    total_files = 0
    for commit_hash in out.splitlines():
        commit_hash = commit_hash.strip()
        # 커밋 메시지가 봇이면 제외
        cmd_subj = f'git log -1 --pretty=%s {commit_hash}'
        subj = run(cmd_subj)
        if BOT_REGEX.match(subj):
            continue
        files, _ = files_excluding_gitkeep_at_commit(commit_hash, path)
        total_files += len(files)
    return total_files

def nonbot_commit_count_on_date(date_str, path):
    """해당 날짜(KST) 동안 path를 건드린 '비봇' 커밋 수"""
    since = f"{date_str} 00:00:00 {TZ_OFFSET}"
    until = f"{date_str} 23:59:59 {TZ_OFFSET}"
    cmd = (
        f'git log --since="{since}" --until="{until}" '
        f'--pretty=%s -- "{path}" || true'
    )
    out = run(cmd)
    if not out:
        return 0
    cnt = 0
    for s in out.splitlines():
        s = s.strip()
        if s and not BOT_REGEX.match(s):
            cnt += 1
    return cnt

def latest_nonbot_commit_for_path(path):
    """
    path(예: YYYY-MM-DD/이름)에서 '비봇' 커밋 중 가장 최근의 (날짜, 해시) 반환.
    없으면 (None, None).
    """
    cmd = f'git log --pretty="%H%x09%ad%x09%s" --date=format-local:"%Y-%m-%d" -- "{path}" || true'
    out = run(cmd)
    if not out:
        return None, None
    for line in out.splitlines():
        parts = line.strip().split("\t", 3)
        if len(parts) < 3:
            continue
        h, date_str, subj = parts[0], parts[1], parts[2].strip()
        if not BOT_REGEX.match(subj):
            return date_str, h
    return None, None

# ---------- 스냅샷 & 파일 개수(재귀, .gitkeep 제외) ----------

def files_excluding_gitkeep_at_commit(commit, base_dir):
    """
    특정 커밋에서 base_dir/ 아래 모든 파일 목록(.gitkeep 제외)과,
    .gitkeep 존재 여부를 함께 반환.
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
        if os.path.basename(name) == ".gitkeep":
            has_gitkeep = True
            continue  # 표시/카운트에서 제외
        files.append(name)
    return files, has_gitkeep

def display_total_files_and_has_nonbot(path):
    """
    표시용 누적 파일 수 n 계산:
      - path의 '가장 최근 비봇 커밋' 스냅샷에서 .gitkeep 제외 파일 개수
    또한 비봇 커밋 존재 여부(boolean)도 함께 반환.
    """
    nonbot_date, nonbot_hash = latest_nonbot_commit_for_path(path)
    if not nonbot_hash:
        # 비봇 커밋이 전혀 없으면 표시 n=0
        return 0, False
    files, _ = files_excluding_gitkeep_at_commit(nonbot_hash, path)
    return len(files), True

# ---------- 렌더링 로직 ----------

def find_all_date_dirs():
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
                tds.append(
                    f'<td align="center" valign="top">'
                    f'<div align="right"><sub>{d}</sub></div>'
                    f"</td>"
                )
                continue

            date_str = date_obj.isoformat()
            lines = []
            for name in NAMES:
                path = f"{date_str}/{name}"

                # 1) 누적 파일 수 n (레트로 포함), 비봇 커밋 존재 여부
                n, has_nonbot = display_total_files_and_has_nonbot(path)

                # 2) 당일 비봇 커밋 수
                today_nonbot_cnt = nonbot_commit_count_on_date(date_str, path)
                
                # 2-1) 당일 비봇 커밋의 파일 개수 
                today_file_cnt = files_in_nonbot_commits_on_date(date_str, path)

                # 3) 색상 결정
                if not has_nonbot:
                    dot = DOT_RED         # 비봇 커밋 없음 → 빨강, n=0
                    n = 0
                elif n >= GOAL_M:
                    if today_nonbot_cnt >= GOAL_M or today_file_cnt >= GOAL_M:
                        dot = DOT_GREEN   # 당일 3커밋 이상 + 누적 3개 이상
                    else:
                        dot = DOT_ORANGE  # 누적 3개 이상, 당일 3커밋 미만(레트로 포함 달성)
                else:
                    dot = DOT_YELLOW      # 비봇 커밋은 있으나 누적 < 3

                # 4) 표시 (이름: 🟢 (n/3)) 단순 형식
                lines.append(
                    f"<div style='font-size:13px'>{name}: {dot} (<code>{n}/{GOAL_M}</code>)</div>"
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
        "🟢 : 당일에 모두 태스크 완료 | "
        "🟠 : 당일에 다 못했으나, 다른날에 모두 태스크 완료 | "
        "🟡 : 당일에 다 못했고, 다른날에도 다 태스크 못했을때 | "
        "🔴 : 아에 안했을때 "
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
