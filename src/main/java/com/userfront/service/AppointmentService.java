package com.userfront.service;

import com.userfront.domain.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by maripen on 2016. 12. 26..
 */
public interface AppointmentService {
    void createAppointment(Appointment appointment);

    List<Appointment> findAll();

    Appointment findAppointment(Long id);

    void confirmAppointment(Long id);
}
