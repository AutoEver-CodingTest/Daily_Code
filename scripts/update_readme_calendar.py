#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import subprocess
import datetime
import calendar
import os
import re

# ===== 설정 =====
NAMES = ["곽태근", "김호집", "오창은", "김태민", "추창우", "김대환", "김소희", "유현아"]
READ_ME = "README.md"
START_MARK = "<!-- PROGRESS_START -->"
END_MARK = "<!-- PROGRESS_END -->"
TZ_OFFSET = "+0900"  # Asia/Seoul (KST)
WEEK_START = calendar.SUNDAY  # 달력 시작: 일요일
DOT_GREEN  = "🟢"  # 그날 3커밋 이상 + 누적 3개 이상
DOT_ORANGE = "🟠"  # 누적 3개 이상(레트로 달성 포함), 당일 3커밋 미만
DOT_YELLOW = "🟡"  # 비봇 커밋은 있으나 누적 < 3
DOT_RED    = "🔴"  # 비봇 커밋 없음 (표시 n=0)
GOAL_M = 3         # 항상 3
PENALTY_PER_SHORT = 1000  # 미달 1개당 1,000원
# ==============

# 달력 첫 요일 설정 (전역)
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

# ---- 특정 시각 기준 스냅샷(벌금 판정에 사용) ----

def files_count_as_of(path, cutoff_dt_kst):
    """
    cutoff_dt_kst(KST, naive date/datetime 허용) 시각 이전(포함) 마지막 커밋 기준으로
    path 아래 .gitkeep 제외 파일 개수를 반환.
    레트로 채우기(다음날 보완) 판정을 위해 사용.
    """
    if isinstance(cutoff_dt_kst, datetime.date) and not isinstance(cutoff_dt_kst, datetime.datetime):
        cutoff_str = f"{cutoff_dt_kst.isoformat()} 23:59:59 {TZ_OFFSET}"
    else:
        # datetime인 경우 그대로 사용
        cutoff_str = cutoff_dt_kst.strftime(f"%Y-%m-%d %H:%M:%S {TZ_OFFSET}")

    # cutoff 이전(포함) path 관련 마지막 커밋 해시
    cmd = f'git rev-list -1 --before="{cutoff_str}" HEAD -- "{path}" || true'
    out = run(cmd)
    commit = out.strip()
    if not commit:
        return 0
    files, _ = files_excluding_gitkeep_at_commit(commit, path)
    return len(files)

# ---------- 날짜/주차 유틸 ----------

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

def _weekday_headers():
    """
    calendar.firstweekday()는 월=0 … 일=6 (월요일 기준) 스킴을 사용함.
    헤더를 월요일 기준 배열로 두고 firstweekday로 회전한다.
    """
    base_mon_first = ["월", "화", "수", "목", "금", "토", "일"]  # 월=0
    fw = calendar.firstweekday()
    return base_mon_first[fw:] + base_mon_first[:fw]

def is_weekday(date_obj):
    # 월(0)~금(4)만 True
    return date_obj.weekday() <= 4

# ---------- 벌금 계산 로직 ----------

def compute_penalty_for_day(name, date_obj, today_kst):
    """
    특정 사람(name)의 특정 날짜(date_obj)에 대한 벌금(원)을 계산.
    규칙:
      - 평일만 대상 (주말은 0원)
      - 목표: 3개
      - D일 목표 미달이어도 D+1 23:59:59 KST까지 3개 채우면 벌금 0원
      - D+2(유예 다음날의 다음날)부터 미달이 확정되며, 미달 1개당 1,000원
    """
    if not is_weekday(date_obj):
        return 0

    # 유예 마감일(D+1) 기준으로 스냅샷을 본다.
    grace_deadline = date_obj + datetime.timedelta(days=1)

    # 아직 유예 기간이 끝나지 않았으면 벌금 계산 보류(0원)
    # (오늘 기준 날짜 비교: D+2 <= today 여야 확정)
    if date_obj + datetime.timedelta(days=2) > today_kst:
        return 0

    date_str = date_obj.isoformat()
    path = f"{date_str}/{name}"

    # 유예 마감일 기준 스냅샷에서 파일 개수
    as_of_count = files_count_as_of(path, datetime.datetime(grace_deadline.year, grace_deadline.month, grace_deadline.day, 23, 59, 59))
    short = max(0, GOAL_M - as_of_count)
    return short * PENALTY_PER_SHORT

def compute_week_penalties(year, month, week_day_numbers, today_kst):
    """
    달력 한 주(week)의 셀 숫자 배열(예: [0,1,2,3,4,5,6] 형태)과 연/월을 받아
    사람별 주간 벌금 합계를 dict로 반환.
    """
    penalties = {name: 0 for name in NAMES}
    # week_day_numbers는 monthcalendar의 한 주(7칸) 숫자들 (0은 타월/빈칸)
    for i, d in enumerate(week_day_numbers):
        if d == 0:
            continue
        date_obj = datetime.date(year, month, d)
        # 월 경계 주라도, 해당 월에 실제 날짜가 있는 칸만 고려
        for name in NAMES:
            penalties[name] += compute_penalty_for_day(name, date_obj, today_kst)
    return penalties

# ---------- 렌더링 로직 ----------

def build_month_calendar(year, month, today_kst):
    cal = calendar.monthcalendar(year, month)
    header_days = _weekday_headers()

    # 헤더에 '벌금' 추가(주별 합계 표시 열)
    header_html = "<thead><tr>" + "".join([f"<th>{d}</th>" for d in header_days]) + "<th>벌금(주)</th></tr></thead>"

    rows_html = []
    for week in cal:
        tds = []
        # 본문: 일~토~일(설정에 따름) 각각 렌더링
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
                
                # 2-1) 당일 비봇 커밋들의 파일 개수 합
                today_file_cnt = files_in_nonbot_commits_on_date(date_str, path)

                # 3) 색상 결정
                if not has_nonbot:
                    dot = DOT_RED         # 비봇 커밋 없음 → 빨강
                    n = 0
                elif today_nonbot_cnt >= GOAL_M or today_file_cnt >= GOAL_M:
                    dot = DOT_GREEN       # 커밋 1개 이상 + 파일 ≥ 목표 → 초록
                elif n >= GOAL_M:
                    dot = DOT_ORANGE      # 누적 목표 달성, 당일/과거 파일 부족 → 주황
                else:
                    dot = DOT_YELLOW      # 비봇 커밋은 있으나 누적 < 목표 → 노랑

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

        # ---- 주간 벌금 열 생성 ----
        week_penalties = compute_week_penalties(year, month, week, today_kst)
        # 이 주(week)의 범위를 텍스트로 표시 (해당 월 내의 유효 날짜만)
        week_dates_in_month = [datetime.date(year, month, d) for d in week if d != 0]
        if week_dates_in_month:
            week_range_text = f"{week_dates_in_month[0].isoformat()} ~ {week_dates_in_month[-1].isoformat()}"
        else:
            week_range_text = ""

        penalty_lines = [f"<div style='font-size:13px'>{name}: {amt:,}원</div>" for name, amt in week_penalties.items()]
        penalty_cell = (
            '<td align="left" valign="top" style="min-width:160px">'
            f"<div><sub>{week_range_text}</sub></div>"
            + "".join(penalty_lines) +
            "</td>"
        )
        tds.append(penalty_cell)

        rows_html.append("<tr>" + "".join(tds) + "</tr>")

    month_title = f"### {year}-{month:02d} 코딩테스트 달력 (KST)"
    legend = (
        "<sub>"
        "🟢 : 당일에 모두 태스크 완료 | "
        "🟠 : 당일에 다 못했으나, 다른날에 모두 태스크 완료 | "
        "🟡 : 당일에 다 못했고, 다른날에도 다 태스크 못했을 때 | "
        "🔴 : 아예 안 했을 때<br>"
        f"벌금 규칙: 평일 기준 1일 목표 {GOAL_M}개, 1개 미달당 {PENALTY_PER_SHORT:,}원. "
        "미달은 다음날 23:59:59 KST까지 보완 시 면제, 그 이후 확정."
        "</sub>"
    )
    table_html = (
        f"{month_title}\n\n"
        + legend + "\n\n"
        + '<table>'
        + header_html
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
    # 오늘(로컬) KST 날짜
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
