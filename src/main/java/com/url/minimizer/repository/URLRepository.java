package com.url.minimizer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.url.minimizer.entity.URL;

@Repository
public interface URLRepository extends JpaRepository<URL,Long> {
	Optional<URL> findTopByOrderByIdDesc();
	Optional<URL> findByMinimizedUrl(String minimizedURL);
}
