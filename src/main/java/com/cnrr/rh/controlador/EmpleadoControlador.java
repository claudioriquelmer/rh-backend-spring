package com.cnrr.rh.controlador;

import com.cnrr.rh.excepcion.RecursoNoEncontradoExcepcion;
import com.cnrr.rh.modelo.Empleado;
import com.cnrr.rh.servicio.IEmpleadoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
//http://localhost:8080/rh-app/
@RequestMapping("rh-app")
@CrossOrigin(value = "http://localhost:3000")
public class EmpleadoControlador {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoControlador.class);

    @Autowired
    private IEmpleadoServicio empleadoServicio;

    //http://localhost:8080/rh-app/empleados
    @GetMapping("/empleados")
    public List<Empleado> obtenerEmpleados() {
        var empleados = empleadoServicio.listarEmpleados();
        empleados.forEach((empleado -> logger.info(empleado.toString())));
        return empleados;
    }

    @PostMapping("/empleados")
    public Empleado agregarEmpleado(@RequestBody Empleado empleado) {
        logger.info("Empleado a agregar : " + empleado);
        return empleadoServicio.guardarEmpleado(empleado);
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable Integer id) {
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if(empleado ==null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id : " + id);
        return ResponseEntity.ok(empleado);
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Integer id, @RequestBody Empleado empleadoRecibido) {
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if(empleado == null)
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe : " + id);
        empleado.setDepartamento(empleadoRecibido.getDepartamento());
        empleado.setSueldo(empleadoRecibido.getSueldo());
        empleadoServicio.guardarEmpleado(empleado);
        return ResponseEntity.ok(empleado);
    }
}
