package com.takykulgam.ugur_v2.core.boundaries.input.auth;

import com.takykulgam.ugur_v2.core.boundaries.dto.AuthStaff;
import reactor.core.publisher.Mono;

public interface AuthStaffLoginCase {

    Mono<Void> login(AuthStaff staff);
}
