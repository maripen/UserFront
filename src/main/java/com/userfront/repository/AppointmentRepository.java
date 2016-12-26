package com.userfront.repository;

import com.userfront.domain.Appointment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by maripen on 2016. 12. 26..
 */
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    List<Appointment> findAll();

    Appointment findOne(Long id);

}
