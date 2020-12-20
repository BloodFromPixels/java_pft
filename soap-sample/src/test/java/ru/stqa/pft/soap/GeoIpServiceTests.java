package ru.stqa.pft.soap;

import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class GeoIpServiceTests {

  @Test
  public void testMyIp() {
    String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("194.28.29.152");
    assertTrue(ipLocation.contains("RU"));
  }

  @Test
  public void testInvalidIp() {
    String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("194.28.29.xxx");
    assertTrue(ipLocation.contains("US"));
  }
}
