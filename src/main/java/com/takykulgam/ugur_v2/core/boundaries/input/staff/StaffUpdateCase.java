package com.takykulgam.ugur_v2.core.boundaries.input.staff;

import com.takykulgam.ugur_v2.core.boundaries.dto.InputStaff;
import reactor.core.publisher.Mono;

public interface StaffUpdateCase {

    Mono<Void> execute(Mono<InputStaff> input);
}
