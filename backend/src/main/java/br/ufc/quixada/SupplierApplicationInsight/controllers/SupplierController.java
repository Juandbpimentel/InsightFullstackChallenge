package br.ufc.quixada.SupplierApplicationInsight.controllers;

import br.ufc.quixada.SupplierApplicationInsight.models.Supplier;
import br.ufc.quixada.SupplierApplicationInsight.services.SupplierService;
import br.ufc.quixada.SupplierApplicationInsight.types.dto.supplier.SupplierCreateRequestDTO;
import br.ufc.quixada.SupplierApplicationInsight.types.dto.supplier.SupplierUpdateRequestDTO;
import br.ufc.quixada.SupplierApplicationInsight.types.enums.SupplyType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping(produces = "application/json", path = "/list")
    public ResponseEntity<List<Supplier>> listSuppliers() {
        return ResponseEntity.ok(supplierService.listSuppliers());
    }

    @PostMapping(consumes = "application/json", produces = "application/json", path = "/create")
    public ResponseEntity<Supplier> createSupplier(@RequestBody @Valid SupplierCreateRequestDTO body) {
        List<SupplyType> supplierSupplyTypes = body.supplyTypes().stream().map(SupplyType::valueOf).toList();
        Supplier supplier = supplierService.createSupplier(new Supplier(body.name(), body.CNPJ(), body.phone(), body.email(), body.address(), supplierSupplyTypes));
        return ResponseEntity.status(HttpStatus.CREATED).body(supplier);
    }

    @GetMapping(produces = "application/json", path = "/{id}")
    public ResponseEntity<Supplier> getSupplier(@PathVariable String id) {
        Supplier supplier = supplierService.getSupplierById(id);
        return ResponseEntity.ok().body(supplier);
    }

    @PutMapping(consumes = "application/json", produces = "application/json", path = "/update/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable String id, @RequestBody @Valid SupplierUpdateRequestDTO body) {
        Supplier supplier = supplierService.updateSupplier(id, body.toMap());
        return ResponseEntity.ok().body(supplier);
    }

    @DeleteMapping(produces = "application/json", path = "/delete/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable String id) {
        supplierService.deleteSupplierById(id);
        return ResponseEntity.ok().build();
    }

}
