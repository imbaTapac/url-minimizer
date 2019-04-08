package com.url.minimizer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.url.minimizer.entity.Statistic;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic,Long> {
	List<Statistic> findAllByUrlId(Long urlId);
}
