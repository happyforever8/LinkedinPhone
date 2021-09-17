package com.rui;

import java.util.*;

public class EmployeeFreeTimeWithRange {
    static class Interval {
        public int start;
        public int end;

        public Interval() {}

        public Interval(int _start, int _end) {
            start = _start;
            end = _end;
        }
    };
    // https://leetcode.com/problems/employee-free-time/description/
    // 加上一个时间范围。

    // 加上了一个时间范围
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule, int startTime, int endTime) {
        PriorityQueue<Interval> pq = new PriorityQueue<>((a, b) -> a.start == b.start ? Integer.compare(a.end, b.end) : Integer.compare(a.start, b.start));
        for (List<Interval> s : schedule) {
            for (Interval i : s) {
                // 这里一定不要等于，要后面这个busy interval 作为free time 的边界
                if (i.end < startTime || i.start > endTime) {
                    continue;
                }
                pq.offer(i);
            }
        }
        Interval cur = new Interval(startTime, startTime);
        List<Interval> res = new ArrayList<>();
        while (!pq.isEmpty()) {
            if (pq.peek().start > cur.end) {
                res.add(new Interval(cur.end, pq.peek().start));
                cur = pq.remove();
            } else {
                cur.end = Math.max(pq.remove().end, cur.end);
            }
        }
        if (cur.end < endTime) {
            res.add(new Interval(cur.end, endTime));
        }
        return res;
    }

    // 原版
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        PriorityQueue<Interval> pq = new PriorityQueue<>((a, b) -> a.start == b.start ? Integer.compare(a.end, b.end) : Integer.compare(a.start, b.start));
        for (List<Interval> s : schedule) {
            for (Interval i : s) {
                pq.offer(i);
            }
        }
        Interval cur = pq.remove();
        List<Interval> res = new ArrayList<>();
        while (!pq.isEmpty()) {
            if (pq.peek().start > cur.end) {
                res.add(new Interval(cur.end, pq.peek().start));
                cur = pq.remove();
            } else {
                cur.end = Math.max(pq.remove().end, cur.end);
            }
        }
        return res;
    }
}
