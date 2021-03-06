package com.example.admediation;

import com.example.admediation.adnetworks.AdNetwork;
import com.example.admediation.adnetworks.AdNetworkRepository;
import com.example.admediation.adunits.AdType;
import com.example.admediation.adunits.AdUnit;
import com.example.admediation.adunits.AdUnitRepository;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(AdNetworkRepository adNetworkRepository, AdUnitRepository adUnitRepository) {

    return args -> {
      log.info("Preloading test data started!");

      // Admob
      AdNetwork adMob = new AdNetwork(1L, "AdMob", 9);
      adNetworkRepository.save(adMob);
      adUnitRepository.save(new AdUnit("US", calcPriorityScore(), AdType.BANNER, adMob));
      adUnitRepository.save(new AdUnit("DE", calcPriorityScore(), AdType.INTERSTITIAL, adMob));
      adUnitRepository.save(new AdUnit("SI", calcPriorityScore(), AdType.BANNER, adMob));
      adUnitRepository.save(new AdUnit("CN", calcPriorityScore(), AdType.BANNER, adMob));
      // AdMob-OptOut
      AdNetwork adMobOptOut = new AdNetwork(2L, "AdMob-OptOut", 9);
      adMobOptOut.setPriorityNetworkId(1L);
      adNetworkRepository.save(adMobOptOut);
      adUnitRepository.save(new AdUnit("SI", calcPriorityScore(), AdType.BANNER, adMobOptOut));
      adUnitRepository.save(new AdUnit("AU", calcPriorityScore(), AdType.BANNER, adMobOptOut));
      // Facebook
      AdNetwork facebook = new AdNetwork(3L, "Facebook", 1);
      facebook.setBlockedCountries("CN");
      adNetworkRepository.save(facebook);
      adUnitRepository.save(new AdUnit("SI", calcPriorityScore(), AdType.BANNER, facebook));
      adUnitRepository.save(new AdUnit("US", calcPriorityScore(), AdType.BANNER, facebook));
      adUnitRepository.save(new AdUnit("CN", calcPriorityScore(), AdType.BANNER, facebook));
      // Unity
      AdNetwork unity = new AdNetwork(4L, "Unity", 1);
      adNetworkRepository.save(unity);
      adUnitRepository.save(new AdUnit("US", calcPriorityScore(), AdType.BANNER, unity));
      adUnitRepository.save(new AdUnit("DE", calcPriorityScore(), AdType.REWARDED, unity));
      adUnitRepository.save(new AdUnit("CN", calcPriorityScore(), AdType.INTERSTITIAL, unity));

      log.info("Preloading test data finished!");
    };
  }

  private int calcPriorityScore() {
    return new Random().nextInt(1000);
  }
}