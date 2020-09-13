package com.vimcar.client.server;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.vimcar.client.util.Constants.PROFILE_TEST;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(PROFILE_TEST)
@Tag("integration-test")
class ServerClientIntegrationTest {

    @Autowired
    private ServerClient server;

    private WireMockServer wireMock = new WireMockServer(8090);

    @BeforeEach
    public void setUp() {
        wireMock.start();
    }

    @AfterEach
    public void windUp() {
        wireMock.stop();
    }

    @Test
    public void shouldRespondToSuccessfulRequest() {
        mockSuccessfulSendMessage();
        assertTrue(server.sendMessage("Testing"), "should return true");
    }

    @Test
    public void shouldRespondToUnSuccessfulRequest() {
        mockErrorSendMessage();
        assertFalse(server.sendMessage("Testing"), "should return false");
    }

    private void mockSuccessfulSendMessage() {
        wireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/message")).willReturn(
                WireMock.status(200)));
    }

    private void mockErrorSendMessage() {
        wireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/message")).willReturn(
                WireMock.status(500)));
    }
}
