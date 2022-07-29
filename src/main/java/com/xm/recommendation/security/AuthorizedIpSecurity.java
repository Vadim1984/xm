package com.xm.recommendation.security;

import com.xm.recommendation.model.IpAddressModel;
import com.xm.recommendation.repository.IpAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AuthorizedIpSecurity {

    private final IpAddressRepository ipAddressRepository;

    public boolean check(Authentication authentication){
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        String userIp = details.getRemoteAddress();

        IpAddressModel deniedIpAddress = ipAddressRepository.findByIpAddress(userIp);

        return Optional.ofNullable(deniedIpAddress)
                .isEmpty();
    }
}
