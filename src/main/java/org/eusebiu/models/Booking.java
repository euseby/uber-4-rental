package org.eusebiu.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "bookings_gen")
    @jakarta.persistence.SequenceGenerator(name = "bookings_gen", sequenceName = "bookings_seq", allocationSize = 50)
    private Long id;
    private Long userId;
    private Long vehicleId;
    private String startDate;
    private String endDate;
    private String status;

    public Long  getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public Long getUserId()
    {
        return userId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }
    public Long getVehicleId()
    {
        return vehicleId;
    }
    public void setVehicleId(Long vehicleId)
    {
        this.vehicleId = vehicleId;
    }
    public String getStartDate()
    {
        return startDate;
    }
    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }
    public String getEndDate()
    {
        return endDate;
    }
    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
}
