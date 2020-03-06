/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backbeans;

import ejb.PeriodoDAO;
import entidades.Periodo;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author santiagoat
 */
@Named(value = "agregar_eliminarPeriodo")
@ViewScoped
public class agregar_eliminarPeriodo implements Serializable {

    @EJB
    private PeriodoDAO servicio;
    private Periodo per;
    private boolean hayActivo;
    private boolean isExecuted;
    private boolean validFechaIni;
    private boolean existePeriodo;
    private List<String> meses;
    private String meselect;
    private boolean isVerif;
    private boolean isCerrado;
    public agregar_eliminarPeriodo() {
    }

    @PostConstruct
    public void Iniciar() {
        isCerrado=false;
        isVerif = false;
        setPer(new Periodo());
        setHayActivo(false);
        setValidFechaIni(false);
        setExistePeriodo(false);
        Date factual = null;
        List<Periodo> peri = getServicio().traerActivo();
        setMeses(new ArrayList<>());
        if (peri.size() > 0) {
            try {
                setPer(peri.get(0));
                setHayActivo(true);
                DateFormat df = new SimpleDateFormat("MM");
                int mes = Integer.parseInt(df.format(per.getPeriodo()));
                switch (mes) {
                    case 1:
                        meselect = "Enero";
                        break;
                    case 2:
                        meselect = "Febrero";
                        break;
                    case 3:
                        meselect = "Marzo";
                        break;
                    case 4:
                        meselect = "Abril";
                        break;
                    case 5:
                        meselect = "Mayo";
                        break;
                    case 6:
                        meselect = "Junio";
                        break;
                    case 7:
                        meselect = "Julio";
                        break;
                    case 8:
                        meselect = "Agosto";
                        break;
                    case 9:
                        meselect = "Septiembre";
                        break;
                    case 10:
                        meselect = "Octubre";
                        break;
                    case 11:
                        meselect = "Noviembre";
                        break;
                    case 12:
                        meselect = "Diciembre";
                        break;
                }
                Date f = getPer().getFechaInicio();
                int mees = f.getMonth();
                for (int i = 1; i <= mes + 1; i++) {
                    switch (i) {
                        case 1:
                            getMeses().add("Enero");
                            break;
                        case 2:
                            getMeses().add("Febrero");
                            break;
                        case 3:
                            getMeses().add("Marzo");
                            break;
                        case 4:
                            getMeses().add("Abril");
                            break;
                        case 5:
                            getMeses().add("Mayo");
                            break;
                        case 6:
                            getMeses().add("Junio");
                            break;
                        case 7:
                            getMeses().add("Julio");
                            break;
                        case 8:
                            getMeses().add("Agosto");
                            break;
                        case 9:
                            getMeses().add("Septiembre");
                            break;
                        case 10:
                            getMeses().add("Octubre");
                            break;
                        case 11:
                            getMeses().add("Noviembre");
                            break;
                        case 12:
                            getMeses().add("Diciembre");
                            break;
                    }
                }
            } catch (NumberFormatException e) {
            }
        }
    }

    public void verificar() {
        isVerif = true;
        Date f = getPer().getFechaInicio();
        int mes = f.getMonth();
        for (int i = 1; i <= mes + 1; i++) {
            switch (i) {
                case 1:
                    getMeses().add("Enero");
                    break;
                case 2:
                    getMeses().add("Febrero");
                    break;
                case 3:
                    getMeses().add("Marzo");
                    break;
                case 4:
                    getMeses().add("Abril");
                    break;
                case 5:
                    getMeses().add("Mayo");
                    break;
                case 6:
                    getMeses().add("Junio");
                    break;
                case 7:
                    getMeses().add("Julio");
                    break;
                case 8:
                    getMeses().add("Agosto");
                    break;
                case 9:
                    getMeses().add("Septiembre");
                    break;
                case 10:
                    getMeses().add("Octubre");
                    break;
                case 11:
                    getMeses().add("Noviembre");
                    break;
                case 12:
                    getMeses().add("Diciembre");
                    break;
            }
        }
    }

    public void abrirPeriodo() {
        setIsExecuted(true);
        try {
            Date factual = null;
            String actual = getServicio().getDate();
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            factual = df.parse(actual);
            int dif = getServicio().datediff(getPer().getFechaInicio(), factual);
            DateFormat dfe = new SimpleDateFormat("yyyy");
            String anio = dfe.format(getPer().getFechaInicio());
            String cad = "";
            switch (getMeselect()) {
                case "Enero":
                    cad = "1/";
                    break;
                case "Febrero":
                    cad = "2/";
                    break;
                case "Marzo":
                    cad = "3/";
                    break;
                case "Abril":
                    cad = "4/";
                    break;
                case "Mayo":
                    cad = "5/";
                    break;
                case "Junio":
                    cad = "6/";
                    break;
                case "Julio":
                    cad = "7/";
                    break;
                case "Agosto":
                    cad = "8/";
                    break;
                case "Septiembre":
                    cad = "9/";
                    break;
                case "Octubre":
                    cad = "10/";
                    break;
                case "Noviembre":
                    cad = "11/";
                    break;
                case "Diciembre":
                    cad = "12/";
                    break;
            }
            cad = cad + "" + anio;
            DateFormat fechacer = new SimpleDateFormat("MM/yyyy");
            Date insert = fechacer.parse(cad);
            getPer().setPeriodo(insert);
            List<Periodo> peri = servicio.existe(insert);
            if (dif < 0) {
                setValidFechaIni(false);
            } else {
                String[] myactual = servicio.extraerMA(factual);
                String[] myaper = servicio.extraerMA(per.getFechaInicio());
                if (myaper[0].equals(myactual[0]) && myactual[1].equals(myaper[1])) {
                    setValidFechaIni(true);
                } else {
                    setValidFechaIni(false);
                }
            }
            if (isValidFechaIni() && peri.isEmpty()) {
                getPer().setEstado(true);
                getServicio().agregar(getPer());
                setHayActivo(true);
                setIsExecuted(false);
            } else {
                if (peri.size() > 0) {
                    existePeriodo = true;
                }
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public void cerrarPeriodo() throws ParseException {
        Date factual = null;
        String actual = getServicio().getDate();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        factual = df.parse(actual);
        per.setFechaCierre(factual);
        per.setEstado(false);
        servicio.modificar(per);
        hayActivo = false;
        isCerrado=true;
    }
    public String convertFecha() {
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        String fecha = df.format(per.getFechaCierre());
        return fecha;
    }

    /**
     * @return the servicio
     */
    public PeriodoDAO getServicio() {
        return servicio;
    }

    /**
     * @param servicio the servicio to set
     */
    public void setServicio(PeriodoDAO servicio) {
        this.servicio = servicio;
    }

    /**
     * @return the per
     */
    public Periodo getPer() {
        return per;
    }

    /**
     * @param per the per to set
     */
    public void setPer(Periodo per) {
        this.per = per;
    }

    /**
     * @return the hayActivo
     */
    public boolean isHayActivo() {
        return hayActivo;
    }

    /**
     * @param hayActivo the hayActivo to set
     */
    public void setHayActivo(boolean hayActivo) {
        this.hayActivo = hayActivo;
    }

    /**
     * @return the isExecuted
     */
    public boolean isIsExecuted() {
        return isExecuted;
    }

    /**
     * @param isExecuted the isExecuted to set
     */
    public void setIsExecuted(boolean isExecuted) {
        this.isExecuted = isExecuted;
    }

    /**
     * @return the validFechaIni
     */
    public boolean isValidFechaIni() {
        return validFechaIni;
    }

    /**
     * @param validFechaIni the validFechaIni to set
     */
    public void setValidFechaIni(boolean validFechaIni) {
        this.validFechaIni = validFechaIni;
    }

    /**
     * @return the existePeriodo
     */
    public boolean isExistePeriodo() {
        return existePeriodo;
    }

    /**
     * @param existePeriodo the existePeriodo to set
     */
    public void setExistePeriodo(boolean existePeriodo) {
        this.existePeriodo = existePeriodo;
    }

    /**
     * @return the meses
     */
    public List<String> getMeses() {
        return meses;
    }

    /**
     * @param meses the meses to set
     */
    public void setMeses(List<String> meses) {
        this.meses = meses;
    }

    /**
     * @return the meselect
     */
    public String getMeselect() {
        return meselect;
    }

    /**
     * @param meselect the meselect to set
     */
    public void setMeselect(String meselect) {
        this.meselect = meselect;
    }

    /**
     * @return the isVerif
     */
    public boolean isIsVerif() {
        return isVerif;
    }

    /**
     * @param isVerif the isVerif to set
     */
    public void setIsVerif(boolean isVerif) {
        this.isVerif = isVerif;
    }

    /**
     * @return the isCerrado
     */
    public boolean isIsCerrado() {
        return isCerrado;
    }

    /**
     * @param isCerrado the isCerrado to set
     */
    public void setIsCerrado(boolean isCerrado) {
        this.isCerrado = isCerrado;
    }
}
