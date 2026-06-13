package com.DATN.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.DATN.DTO.responseDTO.AttendanceCheckOutResponseDTO;
import com.DATN.DTO.responseDTO.AttendanceHistoryResponseDTO;
import com.DATN.DTO.responseDTO.AttendanceResponseDTO;
import com.DATN.entites.Account;
import com.DATN.entites.Attendance;
import com.DATN.entites.Employee;
import com.DATN.repositories.AccountRepository;
import com.DATN.repositories.AttendanceRepository;
import com.DATN.repositories.EmployeeRepository;
import com.DATN.security.util.JwtUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AccountRepository accountRepository;
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public AttendanceResponseDTO checkIn(String token) {
        Integer accountId =
        jwtUtil.extractAccountId(token);

        Account account =
                accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Không tìm thấy tài khoản"));
        Employee employee =
                employeeRepository.findByAccount(account)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Không tìm thấy nhân viên"));
        LocalDate today = LocalDate.now();

        if (attendanceRepository.existsByEmployeeAndDate(
                employee,
                today)) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Hôm nay đã chấm công rồi");
        }

        LocalTime now = LocalTime.now();

        Attendance attendance = new Attendance();

        attendance.setEmployee(employee);
        attendance.setDate(today);
        attendance.setCheckIn(now);

        LocalTime standardTime =
                LocalTime.of(8, 0);

        if (now.isAfter(standardTime)) {
            attendance.setStatus("LATE");
        } else {
            attendance.setStatus("VALID");
        }

        Attendance savedAttendance =
                attendanceRepository.save(attendance);

        return new AttendanceResponseDTO(
                savedAttendance.getId(),
                savedAttendance.getDate(),
                savedAttendance.getCheckIn(),
                savedAttendance.getStatus()
        );
    }

    @Transactional
    public AttendanceCheckOutResponseDTO checkOut(String token) {
        Integer accountId =
                jwtUtil.extractAccountId(token);

        Account account =
                accountRepository.findById(accountId)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Không tìm thấy tài khoản"));

        Employee employee =
                employeeRepository.findByAccount(account)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Không tìm thấy nhân viên"));

        Attendance attendance =
                attendanceRepository
                        .findByEmployeeAndDate(
                                employee,
                                LocalDate.now())
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.BAD_REQUEST,
                                        "Bạn chưa check-in hôm nay"));

        if (attendance.getCheckOut() != null) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Bạn đã check-out rồi");
        }

        attendance.setCheckOut(
                LocalTime.now());

        Attendance savedAttendance =
                attendanceRepository.save(
                        attendance);

        return new AttendanceCheckOutResponseDTO(
                savedAttendance.getId(),
                savedAttendance.getDate(),
                savedAttendance.getCheckIn(),
                savedAttendance.getCheckOut(),
                savedAttendance.getStatus());
        }

        // Lấy lịch sử chấm công bản thân
        public List<AttendanceHistoryResponseDTO>
        getMyHistory(String token) {

        Integer accountId =
                jwtUtil.extractAccountId(token);

        Account account =
                accountRepository.findById(accountId)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Không tìm thấy tài khoản"));

        Employee employee =
                employeeRepository.findByAccount(account)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Không tìm thấy nhân viên"));

        return attendanceRepository
                .findByEmployeeOrderByDateDesc(
                        employee)
                .stream()
                .map(attendance ->
                        new AttendanceHistoryResponseDTO(
                                attendance.getDate(),
                                attendance.getCheckIn(),
                                attendance.getCheckOut(),
                                attendance.getStatus()))
                .toList();
        }
}
