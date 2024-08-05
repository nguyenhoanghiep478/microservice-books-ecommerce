package com.booksms.distributor.start.Controller;

import com.booksms.distributor.common.Data.DTO.DistributorDTO;
import com.booksms.distributor.common.Data.DTO.LicenseDTO;
import com.booksms.distributor.common.Data.DTO.ResponseDTO;
import com.booksms.distributor.common.Service.IDistributorLicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/license")
@RequiredArgsConstructor
public class DistributorLicenseController {
    private final IDistributorLicenseService service;
    @GetMapping("/get-all")
    public ResponseEntity<?> GetAll() {
        //get listDTO
        List<LicenseDTO> results = service.findAll();
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(Arrays.asList("getAllBookSuccessful"))
                .status(201)
                //return list dto
                .result(results)
                .build());
    }
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> GetById(@PathVariable int id) {
        //get bookDTO
        LicenseDTO result = service.findById(id);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(Arrays.asList("getBookByIdSuccessful"))
                .status(200)
                .result(result)
                .build()
        );
    }
    @PostMapping("/create")
    public ResponseEntity<?> Create(@RequestBody LicenseDTO request) {
        LicenseDTO result = service.create(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(Arrays.asList("createDistributorSuccessful"))
                .status(200)
                .result(result)
                .build()
        );
    }
}
