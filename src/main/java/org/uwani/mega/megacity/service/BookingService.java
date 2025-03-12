package org.uwani.mega.megacity.service;



import org.uwani.mega.megacity.dto.BookingDTO;
import org.uwani.mega.megacity.entity.Booking;

import java.util.List;

public interface BookingService {
    boolean createBooking(BookingDTO bookingDTO);
    Booking getBookingById(int id);
    List<Booking> getAllBookings();
    boolean updateBooking(int id, BookingDTO bookingDTO);
    boolean deleteBooking(int id);
    boolean updateBookingStatus(int id, String status);
    List<Booking> getAllBookings(int id);
}
