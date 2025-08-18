#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import subprocess
import datetime
import calendar
import os
import re
from collections import defaultdict

# ===== 설정 =====
NAMES = ["곽태근", "김호집", "오창은", "김태민", "추창우"]
READ_ME = "README.md"
START_MARK = "<!-- PROGRESS_START -->"
END_MARK = "<!-- PROGRESS_END -->"
TZ_OFFSET = "+0900"  # Asia/Seoul (KST)
WEEK_START = calendar.SUNDAY  # 달력 머리: 일 ~ 토
DOT_O = "🟢"  # O
DOT_X = "🔴"  # X
# ==============

calendar.setfirstweekday(WEEK_START)

def run(cmd):
    return subprocess.check_output(cmd, shell=True, text=True, encoding="utf-8").strip()

def git_subjects_for_date_and_path(date_str, path):
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

def judge_day(date_str, name):
    """
    반환: 'O' 또는 'X'
    - 해당 날짜/이름 경로에 커밋이 없거나 봇 메시지만 => 'X'
    - 그 외 메시지 하나라도 => 'O'
    """
    bot_msg = f"{date_str}일자 태스크 배정완료, 화이팅!"
    path = f"{date_str}/{name}"
    subjects = git_subjects_for_date_and_path(date_str, path)
    if not subjects:
        return "X"
    for s in subjects:
        if s != bot_msg:
            return "O"
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
    """start_date의 1일 ~ end_date의 1일까지 월 단위로 이터레이션."""
    y, m = start_date.year, start_date.month
    while (y < end_date.year) or (y == end_date.year and m <= end_date.month):
        yield y, m
        if m == 12:
            y += 1
            m = 1
        else:
            m += 1

def build_month_calendar(year, month, today_kst):
    """
    월별 달력(HTML table) 생성.
    - 어제까지 O/X 확정
    - 오늘 이후 공백
    """
    cal = calendar.monthcalendar(year, month)  # 주: [월..일]이 아니라 설정된 firstweekday 기준
    # GitHub는 기본 마크다운 테이블보다 HTML 테이블이 칸 꾸미기 유리
    header_days = ["일", "월", "화", "수", "목", "금", "토"]
    # firstweekday를 반영해서 회전
    rotate = list(range(7))
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
                # 미래/오늘은 빈 칸
                tds.append(f'<td align="center" valign="top"><div align="right"><sub>{d}</sub></div></td>')
                continue

            date_str = date_obj.isoformat()
            dots = []
            for name in NAMES:
                flag = judge_day(date_str, name)  # 'O' or 'X'
                dot = DOT_O if flag == "O" else DOT_X
                title = f'{name}: {flag}'
                dots.append(f'<span title="{title}">{dot}</span>')
            tds.append(
                '<td align="center" valign="top" style="min-width:96px">'
                f'<div align="right"><sub>{d}</sub></div>'
                f'<div style="font-size: 18px; line-height:1.2">{ "".join(dots) }</div>'
                "</td>"
            )
        rows_html.append("<tr>" + "".join(tds) + "</tr>")

    # 범례(이름 순서 고정)
    legend_items = [f'<li><strong>{i+1}</strong>번째 점: {name}</li>' for i, name in enumerate(NAMES)]
    legend_html = (
        '<details><summary>범례 보기</summary>'
        '<ul style="margin-top:6px">'
        + "".join(legend_items) +
        "</ul></details>"
    )

    month_title = f"### {year}-{month:02d} 코딩테스트 달력 (KST)"
    table_html = (
        f"{month_title}\n\n"
        + legend_html + "\n\n"
        + '<table>'
        + "<thead><tr>" + "".join([f"<th>{d}</th>" for d in header_days]) + "</tr></thead>"
        + "<tbody>" + "".join(rows_html) + "</tbody>"
        + "</table>\n"
    )
    return table_html

def build_all_months(today_kst):
    # 리포의 날짜 디렉터리를 스캔해서, 없으면 현재 달만 생성
    date_dirs = find_all_date_dirs()
    if date_dirs:
        start = datetime.date(date_dirs[0].year, date_dirs[0].month, 1)
    else:
        start = datetime.date(today_kst.year, today_kst.month, 1)

    end = datetime.date(today_kst.year, today_kst.month, 1)  # 오늘의 월까지
    blocks = []
    for y, m in month_iter(start, end):
        block = build_month_calendar(y, m, today_kst)
        # 과거 달은 접기, 이번 달은 펼침
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
