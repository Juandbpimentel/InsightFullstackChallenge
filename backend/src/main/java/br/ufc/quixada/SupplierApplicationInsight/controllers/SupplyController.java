package br.ufc.quixada.SupplierApplicationInsight.controllers;


import br.ufc.quixada.SupplierApplicationInsight.models.Supply;
import br.ufc.quixada.SupplierApplicationInsight.services.SupplyService;
import br.ufc.quixada.SupplierApplicationInsight.types.dto.supply.SupplyCreateRequestDTO;
import br.ufc.quixada.SupplierApplicationInsight.types.dto.supply.SupplyUpdateRequestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/supplies")
public class SupplyController {

    @Autowired
    private SupplyService supplyService;

    @GetMapping(produces = "application/json", path = "/list")
    public ResponseEntity<List<Supply>> listSupplies() {
        return ResponseEntity.ok(supplyService.listSupplies());
    }

    @GetMapping(produces = "application/json", path = "/{id}")
    public ResponseEntity<Supply> getSupplyById(@PathVariable String id) {
        return ResponseEntity.ok(supplyService.getSupplyById(id));
    }

    @GetMapping(produces = "application/json", path = "/findBySupplierId/{supplierId}")
    public ResponseEntity<List<Supply>> getSuppliesBySupplierId(@PathVariable String supplierId) {
        return ResponseEntity.ok(supplyService.getSuppliesBySupplierId(supplierId));
    }

    @DeleteMapping(produces = "application/json", path = "/{id}/delete")
    public ResponseEntity<?> deleteSupply(@PathVariable String id) {
        supplyService.deleteSupplyById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = "application/json", produces = "application/json", path = "/{id}/update")
    public ResponseEntity<Supply> updateSupply(@RequestBody SupplyUpdateRequestDTO supplyUpdateRequestDTO, @PathVariable String id) {
        return ResponseEntity.ok(supplyService.updateSupply(id, supplyUpdateRequestDTO.toMap()));
    }

    @PostMapping(produces = "application/json", path = "/create")
    public ResponseEntity<Supply> createSupply(@Valid @RequestBody SupplyCreateRequestDTO supplyCreateRequestDTO) {
        Supply supply = new Supply(supplyCreateRequestDTO.name(), supplyCreateRequestDTO.supplierId(),
                supplyCreateRequestDTO.description(), supplyCreateRequestDTO.supplyDate(),
                supplyCreateRequestDTO.supplyType());
        return ResponseEntity.ok(supplyService.createSupply(supply));
    }
}

/*
* {
  "exception": "java.lang.NullPointerException",
  "errorcode": "internal-server-error",
  "message": "Cannot invoke \"br.ufc.quixada.SupplierApplicationInsight.repositories.SupplierRepository.findById(Object)\" because \"this.supplierRepository\" is null"
}
*
* */