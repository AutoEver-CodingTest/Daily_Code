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
DOT_O = "🟢"  # 해당 날짜에 비봇 커밋 존재
DOT_L = "🟠"  # 해당 날짜엔 없지만 다른 날짜에 비봇 커밋 존재(레트로)
DOT_X = "🔴"  # 봇 커밋만 있거나 커밋 없음
# ==============

calendar.setfirstweekday(WEEK_START)

BOT_REGEX = re.compile(r"^\d{4}-\d{2}-\d{2}일자 태스크 배정완료, 화이팅!$")

def run(cmd):
    return subprocess.check_output(cmd, shell=True, text=True, encoding="utf-8").strip()

def git_subjects_for_date_and_path(date_str, path):
    """특정 날짜 KST 범위의 커밋 subject들(해당 path에 한정)"""
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
    해당 path를 건드린 커밋 중 '봇이 아닌' 가장 최근 커밋의 날짜(YYYY-MM-DD, 로컬=KST)를 반환.
    없으면 None.
    """
    # 워크플로우에서 시스템 타임존을 Asia/Seoul로 설정하므로 --date=format-local 사용
    cmd = f'git log --pretty="%ad%x09%s" --date=format-local:"%Y-%m-%d" -- "{path}" || true'
    out = run(cmd)
    if not out:
        return None
    for line in out.splitlines():
        line = line.strip()
        if not line:
            continue
        try:
            date_part, subject = line.split("\t", 1)
        except ValueError:
            # 탭이 없을 수도 있으니 방어
            parts = line.split(" ", 1)
            if len(parts) == 2:
                date_part, subject = parts
            else:
                continue
        subject = subject.strip()
        # 봇 커밋은 건너뛴다(어떤 날짜든)
        if BOT_REGEX.match(subject):
            continue
        # 비봇 커밋이면 그 커밋의 날짜를 반환
        return date_part
    return None

def judge_day(date_str, name):
    """
    반환: 'O' / 'L' / 'X'
      - 'O' : 해당 날짜에 비봇 커밋 존재
      - 'L' : 해당 날짜엔 없지만, 다른 날짜에 비봇 커밋 존재(레트로)
      - 'X' : 봇 커밋만 있거나 커밋 없음
    """
    path = f"{date_str}/{name}"
    subjects_today = git_subjects_for_date_and_path(date_str, path)

    # 오늘자 범위에서 봇 외 커밋이 하나라도 있으면 O
    for s in subjects_today:
        if not BOT_REGEX.match(s):
            return "O"

    # 오늘자엔 없었으나, 과거/미래 다른 날짜에 비봇 커밋이 있으면 L
    nonbot_any_date = latest_nonbot_commit_date_for_path(path)
    if nonbot_any_date is not None and nonbot_any_date != date_str:
        return "L"

    # 그 외에는 X
    return "X"

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
                flag = judge_day(date_str, name)  # 'O' / 'L' / 'X'
                if flag == "O":
                    dot = DOT_O
                elif flag == "L":
                    dot = DOT_L
                else:
                    dot = DOT_X
                lines.append(f"<div style='font-size:13px'>{name}: {dot}</div>")

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
        "🟢=당일 비봇 커밋, "
        "🟠=다른 날 비봇 커밋(레트로), "
        "🔴=봇만/없음"
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
