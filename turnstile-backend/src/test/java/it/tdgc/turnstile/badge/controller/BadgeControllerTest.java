//package it.tdgc.turnstile.badge.controller;
//
//import it.tdgc.turnstile.controller.BadgeController;
//import it.tdgc.turnstile.dto.BadgeDTO;
//import it.tdgc.turnstile.exception.BadgeAlreadyExistsException;
//import it.tdgc.turnstile.model.Badge;
//import it.tdgc.turnstile.service.BadgeService;
//import it.tdgc.turnstile.util.ApiResponse;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class BadgeControllerTest {
//
//    @Mock
//    private BadgeService badgeService;
//
//    @InjectMocks
//    private BadgeController badgeController;
//
//    // Test for GET /badge/id/{id}
//    @Test
//    public void testGetBadgeById() {
//        Integer id = 1;
//        BadgeDTO badgeDTO = new BadgeDTO(id, "123456789");
//
//        // Mocking the service response
//        ApiResponse<BadgeDTO> apiResponse = new ApiResponse<>("200", "Badge found", badgeDTO, null, null);
//        when(badgeService.getBadgeById(id)).thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));
//
//        // Calling the controller method
//        ResponseEntity<ApiResponse<BadgeDTO>> response = badgeController.getBadgeById(id);
//
//        // Verifying the response
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("200", response.getBody().getStatus());
//        assertEquals("Badge found", response.getBody().getMessage());
//        assertEquals(id, response.getBody().getData().getId());
//    }
//
//    // Test for GET /badge/rfid/{rfid}
//    @Test
//    public void testGetBadgeByRfid() {
//        String rfid = "123456789";
//        BadgeDTO badgeDTO = new BadgeDTO(1, rfid);
//
//        // Mocking the service response
//        ApiResponse<BadgeDTO> apiResponse = new ApiResponse<>("200", "Badge found", badgeDTO, null, null);
//        when(badgeService.getBadgeByRfid(rfid)).thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));
//
//        // Calling the controller method
//        ResponseEntity<ApiResponse<BadgeDTO>> response = badgeController.getBadgeByRfid(rfid);
//
//        // Verifying the response
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("200", response.getBody().getStatus());
//        assertEquals("Badge found", response.getBody().getMessage());
//        assertEquals(rfid, response.getBody().getData().getRfid());
//    }
//
//    // Test for DELETE /badge/delete/id/{id}
//    @Test
//    public void testDeleteBadgeById() {
//        Integer id = 1;
//        BadgeDTO badgeDTO = new BadgeDTO(id, "123456789");
//
//        // Mocking the service response
//        ApiResponse<BadgeDTO> apiResponse = new ApiResponse<>("200", "Badge successfully deleted", badgeDTO, null, null);
//        when(badgeService.deleteBadgeById(id)).thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));
//
//        // Calling the controller method
//        ResponseEntity<ApiResponse<BadgeDTO>> response = badgeController.deleteBadgeById(id);
//
//        // Verifying the response
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("200", response.getBody().getStatus());
//        assertEquals("Badge successfully deleted", response.getBody().getMessage());
//    }
//
//    // Test for DELETE /badge/delete/rfid/{rfid}
//    @Test
//    public void testDeleteBadgeByRfid() {
//        String rfid = "123456789";
//        BadgeDTO badgeDTO = new BadgeDTO(1, rfid);
//
//        // Mocking the service response
//        ApiResponse<BadgeDTO> apiResponse = new ApiResponse<>("200", "Badge successfully deleted", badgeDTO, null, null);
//        when(badgeService.deleteBadgeByRfid(rfid)).thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));
//
//        // Calling the controller method
//        ResponseEntity<ApiResponse<BadgeDTO>> response = badgeController.deleteBadgeByRfid(rfid);
//
//        // Verifying the response
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("200", response.getBody().getStatus());
//        assertEquals("Badge successfully deleted", response.getBody().getMessage());
//    }
//
//    // Test for PUT /badge/update
//    @Test
//    public void testUpdateBadge() {
//        Badge badge = new Badge();
//        badge.setId(1);
//        badge.setRfid("123456789");
//
//        BadgeDTO badgeDTO = new BadgeDTO(1, "123456789");
//
//        // Mocking the service response
//        ApiResponse<BadgeDTO> apiResponse = new ApiResponse<>("204", "Badge updated successfully", badgeDTO, null, null);
//        when(badgeService.updateBadge(badge)).thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));
//
//        // Calling the controller method
//        ResponseEntity<ApiResponse<BadgeDTO>> response = badgeController.updateBadge(badge);
//
//        // Verifying the response
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("204", response.getBody().getStatus());
//        assertEquals("Badge updated successfully", response.getBody().getMessage());
//    }
//
//    // Test for POST /badge/insert
//    @Test
//    public void testInsertBadge() throws BadgeAlreadyExistsException {
//        Badge badge = new Badge();
//        badge.setId(1);
//        badge.setRfid("123456789");
//
//        BadgeDTO badgeDTO = new BadgeDTO(1, "123456789");
//
//        // Mocking the service response
//        ApiResponse<BadgeDTO> apiResponse = new ApiResponse<>("200", "Badge created successfully", badgeDTO, null, null);
//        when(badgeService.insertBadge(badge)).thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));
//
//        // Calling the controller method
//        ResponseEntity<ApiResponse<BadgeDTO>> response = badgeController.insertBadge(badge);
//
//        // Verifying the response
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("200", response.getBody().getStatus());
//        assertEquals("Badge created successfully", response.getBody().getMessage());
//    }
//}
