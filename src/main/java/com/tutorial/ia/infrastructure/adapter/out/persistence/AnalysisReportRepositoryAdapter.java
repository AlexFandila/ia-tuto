package com.tutorial.ia.infrastructure.adapter.out.persistence;

import com.tutorial.ia.domain.model.AnalysisReport;
import com.tutorial.ia.domain.model.ReportType;
import com.tutorial.ia.domain.model.User;
import com.tutorial.ia.domain.port.out.AnalysisReportRepositoryPort;
import com.tutorial.ia.infrastructure.adapter.out.persistence.mapper.AnalysisReportMapper;
import com.tutorial.ia.infrastructure.adapter.out.persistence.mapper.UserMapper;
import com.tutorial.ia.infrastructure.adapter.out.persistence.repository.AnalysisReportJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AnalysisReportRepositoryAdapter implements AnalysisReportRepositoryPort {

    private final AnalysisReportJpaRepository analysisreportRepository;
    private final AnalysisReportMapper analysisReportMapper;
    private final UserMapper userMapper;

    @Override
    public AnalysisReport save(AnalysisReport report) {
        return analysisReportMapper.toDomain(analysisreportRepository.save(analysisReportMapper.toEntity(report)));
    }
    
    @Override
    public Optional<AnalysisReport> findById(Long id) {
        return analysisreportRepository.findById(id)
                .map(analysisReportMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        analysisreportRepository.deleteById(id);
    }

    @Override
    public List<AnalysisReport> findByUser(User user) {
        return analysisreportRepository.findByUser(userMapper.toEntity(user)).stream()
                .map(analysisReportMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<AnalysisReport> findByUserAndTypeAndPeriod(User user, ReportType type, LocalDate periodStart, LocalDate periodEnd) {
        return analysisreportRepository.findByUserAndTypeAndPeriodStartAndPeriodEnd(
                userMapper.toEntity(user), type, periodStart, periodEnd
        ).map(analysisReportMapper::toDomain);
    }

    @Override
    public boolean existsByUserAndTypeAndPeriod(User user, ReportType type, LocalDate periodStart, LocalDate periodEnd) {
        return analysisreportRepository.existsByUserAndTypeAndPeriodStartAndPeriodEnd(userMapper.toEntity(user), type, periodStart, periodEnd);
    }

    @Override
    public List<AnalysisReport> findByUserAndType(User user, ReportType type) {
        return analysisreportRepository.findByUserAndType(userMapper.toEntity(user), type).stream()
                .map(analysisReportMapper::toDomain)
                .toList();
    }

}
