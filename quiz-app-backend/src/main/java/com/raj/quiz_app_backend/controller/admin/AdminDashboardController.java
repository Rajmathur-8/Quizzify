package com.raj.quiz_app_backend.controller.admin;

import com.raj.quiz_app_backend.services.admin.AdminDashboardService;
import com.raj.quiz_app_backend.services.admin.AdminStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;
    private final AdminStatsService adminStatsService;

    @GetMapping("/overview")
    public ResponseEntity<Map<String, Object>> getOverview() {
        return ResponseEntity.ok(adminDashboardService.getDashboardStats());
    }

    @GetMapping("/charts")
    public ResponseEntity<Map<String, Object>> getCharts() {
        return ResponseEntity.ok(adminDashboardService.getChartData());
    }

    @GetMapping("/performance")
    public ResponseEntity<Map<String, Object>> getPerformance() {
        return ResponseEntity.ok(adminDashboardService.getPerformanceSummary());
    }

    @GetMapping("/system")
    public ResponseEntity<Map<String, Object>> getSystemStats() {
        return ResponseEntity.ok(adminStatsService.getSystemStats());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Map<String, Object>> getUserStats(@PathVariable String id) {
        return ResponseEntity.ok(adminStatsService.getUserActivityStats(id));
    }

    @GetMapping("/quiz/{id}")
    public ResponseEntity<Map<String, Object>> getQuizStats(@PathVariable String id) {
        return ResponseEntity.ok(adminStatsService.getQuizPerformanceStats(id));
    }
}