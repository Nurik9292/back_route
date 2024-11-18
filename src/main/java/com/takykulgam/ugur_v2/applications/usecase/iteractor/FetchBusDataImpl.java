package com.takykulgam.ugur_v2.applications.usecase.iteractor;

import com.takykulgam.ugur_v2.applications.data_access.bus.BusRepository;
import com.takykulgam.ugur_v2.applications.usecase.input.FetchBusData;

import java.util.List;

public class FetchBusDataImpl implements FetchBusData {

    private final List<BusRepository> busRepository;

    public FetchBusDataImpl(List<BusRepository> busRepository) {
        this.busRepository = busRepository;
    }

    @Override
    public void fetchBusData() {
         busRepository.forEach(BusRepository::fetch);
    }
}
