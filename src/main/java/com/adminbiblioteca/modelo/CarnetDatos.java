package com.adminbiblioteca.modelo;

    import java.io.File;
    import java.time.LocalDate;

    /**
     * Clase que representa los datos de un carnet.
     */
    public class CarnetDatos {
        private String nombre;
        private String apellidos;
        private String idCliente;
        private LocalDate fechaNacimiento;
        private boolean tieneDiscapacidad;
        private boolean esPremium;
        private String tipoDireccion;
        private String denominacion;
        private String portal;
        private String pisoLetra;
        private String ciudad;
        private String provincia;
        private LocalDate fechaEmision;
        private LocalDate fechaVencimiento;
        private File foto;

        /**
         * Constructor de la clase CarnetDatos.
         *
         * @param nombre el nombre del cliente
         * @param apellidos los apellidos del cliente
         * @param idCliente el ID del cliente
         * @param fechaNacimiento la fecha de nacimiento del cliente
         * @param tieneDiscapacidad indica si el cliente tiene discapacidad
         * @param esPremium indica si el cliente es premium
         * @param tipoDireccion el tipo de dirección (Calle, Avenida, etc.)
         * @param denominacion la denominación de la dirección
         * @param portal el número del portal
         * @param pisoLetra el piso y letra
         * @param ciudad la ciudad
         * @param provincia la provincia
         * @param foto la foto del cliente
         */
        public CarnetDatos(String nombre, String apellidos, String idCliente, LocalDate fechaNacimiento,
                           boolean tieneDiscapacidad, boolean esPremium, String tipoDireccion, String denominacion,
                           String portal, String pisoLetra, String ciudad, String provincia, File foto) {
            this.nombre = nombre;
            this.apellidos = apellidos;
            this.idCliente = idCliente;
            this.fechaNacimiento = fechaNacimiento;
            this.tieneDiscapacidad = tieneDiscapacidad;
            this.esPremium = esPremium;
            this.tipoDireccion = tipoDireccion;
            this.denominacion = denominacion;
            this.portal = portal;
            this.pisoLetra = pisoLetra;
            this.ciudad = ciudad;
            this.provincia = provincia;
            this.foto = foto;
            this.fechaEmision = LocalDate.now();
            this.fechaVencimiento = fechaEmision.plusYears(3);
        }

        // Getters y setters

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getApellidos() {
            return apellidos;
        }

        public void setApellidos(String apellidos) {
            this.apellidos = apellidos;
        }

        public String getIdCliente() {
            return idCliente;
        }

        public void setIdCliente(String idCliente) {
            this.idCliente = idCliente;
        }

        public LocalDate getFechaNacimiento() {
            return fechaNacimiento;
        }

        public void setFechaNacimiento(LocalDate fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
        }

        public boolean isTieneDiscapacidad() {
            return tieneDiscapacidad;
        }

        public void setTieneDiscapacidad(boolean tieneDiscapacidad) {
            this.tieneDiscapacidad = tieneDiscapacidad;
        }

        public boolean isEsPremium() {
            return esPremium;
        }

        public void setEsPremium(boolean esPremium) {
            this.esPremium = esPremium;
        }

        public String getDenominacion() {
            return denominacion;
        }

        public void setDenominacion(String denominacion) {
            this.denominacion = denominacion;
        }

        public String getTipoDireccion() {
            return tipoDireccion;
        }

        public void setTipoDireccion(String tipoDireccion) {
            this.tipoDireccion = tipoDireccion;
        }

        public String getPortal() {
            return portal;
        }

        public void setPortal(String portal) {
            this.portal = portal;
        }

        public String getPisoLetra() {
            return pisoLetra;
        }

        public void setPisoLetra(String pisoLetra) {
            this.pisoLetra = pisoLetra;
        }

        public String getCiudad() {
            return ciudad;
        }

        public void setCiudad(String ciudad) {
            this.ciudad = ciudad;
        }

        public String getProvincia() {
            return provincia;
        }

        public void setProvincia(String provincia) {
            this.provincia = provincia;
        }

        public LocalDate getFechaEmision() {
            return fechaEmision;
        }

        public void setFechaEmision(LocalDate fechaEmision) {
            this.fechaEmision = fechaEmision;
        }

        public LocalDate getFechaVencimiento() {
            return fechaVencimiento;
        }

        public void setFechaVencimiento(LocalDate fechaVencimiento) {
            this.fechaVencimiento = fechaVencimiento;
        }

        public File getFoto() {
            return foto;
        }

        public void setFoto(File foto) {
            this.foto = foto;
        }
    }