package com.takykulgam.ugur_v2.infrastructure.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusAtLogistikaDto {

    private int code;
    private String msg;
    @JsonProperty("list")
    private List<Bus> data;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Bus {
        @JsonProperty("agentid")
        private int agentId;
        @JsonProperty("veh_id")
        private int vehId;
        private String imei;
        @JsonProperty("typeid")
        private int typeId;
        private String type;
        private String model;
        @JsonProperty("vehiclenumber")
        private String vehicleNumber;
        private String folder;
        @JsonProperty("created_time")
        private long createdTime;
        @JsonProperty("current_mileage")
        private long currentMileage;
        @JsonProperty("driver_name")
        private String driverName;
        @JsonProperty("driver_phone")
        private String driverPhone;
        private String info;
        private Status status;
        private List<Sensor> sensors;
        @JsonProperty("sensors_status")
        private List<SensorStatus> sensorStatuses;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Status {
        private String lat;
        private String lon;
        private int speed;
        private int dir;
        private int alt;
        @JsonProperty("satsinview")
        private int satsInView;
        private int firing;
        @JsonProperty("unixtimestamp")
        private String unixTimestamp;

        @Override
        public String toString() {
            return "Status [" +
                    "lat=" + lat + ", " +
                    "lon=" + lon + ", " +
                    "speed=" + speed + ", " +
                    "dir=" + dir + ", " +
                    "alt=" + alt + ", " +
                    "satsInView=" + satsInView + ", " +
                    "firing=" + firing + "]";
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sensor {
        private String name;
        private String description;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SensorStatus {
        private String name;
        private String id;
        @JsonProperty("hum_value")
        private String humValue;
        @JsonProperty("dig_value")
        private String digValue;
        @JsonProperty("raw_value")
        private String rawValue;
        @JsonProperty("change_ts")
        private long changeTs;
        private String group;

        @Override
        public String toString() {
            return "SensorStatus [" +
                    "name=" + name + ", " +
                    "id=" + id + ", " +
                    "humValue=" + humValue + ", " +
                    "digValue=" + digValue + ", " +
                    "rawValue=" + rawValue + ", " +
                    "changeTs=" + changeTs + ", " +
                    "group=" + group + "]";
        }
    }

    @Override
    public String toString() {
        return "BusResponse [code=" + code + ", msg=" + msg + " data=" + data + "]";
    }
}
