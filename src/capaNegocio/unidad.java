/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capaNegocio;

import capaDatos.clsJDBC;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author laboratorio_computo
 */
public class unidad {
    
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    Connection con=null;
    Statement sent;
    
    
    
    public Integer generarCodigoUnidad() throws Exception{
        strSQL = "SELECT COALESCE(MAX(codigounidad),0)+1 AS codigo FROM unidad" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código");
        }
        return 0;
    }
    
    public void registrarunidad(String cod,String nombres,String direccion, Integer codciudad,boolean vigencia , Integer codUnidad, String placa,Integer codmarca, String modelo, Integer año, String color, Integer asientos, boolean estado,Integer codtipo) throws Exception {
        Integer cantidad=0;
        try {
            strSQL="SELECT COUNT(*) AS cantidad FROM cliente WHERE dni='"+cod+"'" ;
            objConectar.conectar();
            con=objConectar.getCon();
            con.setAutoCommit(false);
            sent=con.createStatement();
            rs=sent.executeQuery(strSQL);
            
            while(rs.next()){
                cantidad=rs.getInt("cantidad");
            }
            
            if(cantidad==0){
                JOptionPane.showMessageDialog(null, "El cliente no Existe es necesario Registrarlo");
                if(nombres!= null){
                    String strSQL1="INSERT INTO cliente VALUES('"+cod+"', '"+nombres+"','"+direccion+"', "+codciudad+", "+vigencia+")";
                    JOptionPane.showMessageDialog(null, "Unidad Registrada Correctamente");
                    sent.executeUpdate(strSQL1);
                    
                    String strSQL2="INSERT INTO unidad VALUES("+codUnidad+", '"+placa+"', "+codmarca+", '"+modelo+"', "+año+", '"+color+"', "+asientos+", "+estado+", "+codtipo+", '"+cod+"')";
                    JOptionPane.showMessageDialog(null, "Unidad Registrada Correctamente");
                    sent.executeUpdate(strSQL2);
                }else{
                    JOptionPane.showMessageDialog(null, "Hay datos que no han sido correctamente ingresados");
                }
                
            }else{
                    String strSQL2="INSERT INTO unidad VALUES("+codUnidad+", '"+placa+"', "+codmarca+", '"+modelo+"', "+año+", '"+color+"', "+asientos+", "+estado+", "+codtipo+", '"+cod+"')";
                    sent.executeUpdate(strSQL2);
            }
            con.commit();
            JOptionPane.showMessageDialog(null, "Unidad Registrada Correctamente");
            
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al registrar");
        }finally{
            objConectar.desconectar();
        } 
    }
    
    
    
    public ResultSet buscarcliente(String dni) throws Exception{
        //strSQL = "SELECT * FROM cliente WHERE dni ='"+ dni+"'";
        try {
            objConectar.conectar();
            con = objConectar.getCon();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM cliente WHERE dni = ? ");
            sentencia.setString(1, dni);
            //rs=objConectar.consultarBD(strSQL);
            rs = sentencia.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar cliente");
        }
    }
    
    public ResultSet listarmarca() throws Exception{
        //strSQL = "SELECT * FROM marca";
        try {
            objConectar.conectar();
            con  = objConectar.getCon();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM marca");
            rs=sentencia.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar cliente");
        }
    }
    
    
      public ResultSet listarciudad() throws Exception{
        //strSQL = "SELECT * FROM ciudad";
        try {
            objConectar.conectar();
            con = objConectar.getCon();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM ciudad");
            //rs=objConectar.consultarBD(strSQL);
            rs = sentencia.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar cliente");
        }
    }
      
        public ResultSet listartipo() throws Exception{
        //strSQL = "SELECT * FROM tipo_unidad";
        try {
            objConectar.conectar();
            con = objConectar.getCon();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM tipo_unidad");
            //rs=objConectar.consultarBD(strSQL);
            rs = sentencia.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar cliente");
        }
    }
    
    public Integer obtenerCodigoMarca(String nom) throws Exception{
        //strSQL = "SELECT codigomarca from  marca WHERE nombre='" + nom + "'" ;
        try {
            objConectar.conectar();
            con = objConectar.getCon();
            CallableStatement sentencia = con.prepareCall("SELECT codigomarca from  marca WHERE nombre=?");
            sentencia.setString(1, nom);
            //rs=objConectar.consultarBD(strSQL);
            rs = sentencia.executeQuery();
            if (rs.next()) return rs.getInt("codigomarca");
        } catch (Exception e) {
            throw new Exception("Error al buscar marca");
        }
        return 0;
    }
    
    public Integer obtenerCodigoCiudad(String nom) throws Exception{
        //strSQL = "SELECT codigociudad from  ciudad where nombre='" + nom + "'" ;
        try {
            objConectar.conectar();
            con = objConectar.getCon();
            CallableStatement sentencia = con.prepareCall("SELECT codigociudad from  ciudad where nombre=?");
            sentencia.setString(1, nom);
            //rs=objConectar.consultarBD(strSQL);
            rs = sentencia.executeQuery();
            if (rs.next()) return rs.getInt("codigociudad");
        } catch (Exception e) {
            throw new Exception("Error al buscar ciudad");
        }
        return 0;
    }
    
     public Integer obtenerCodigoTipo(String nom) throws Exception{
        //strSQL = "SELECT codigotipo from  tipo_unidad where nombretipo='" + nom + "'" ;
        try {
            objConectar.conectar();
            con = objConectar.getCon();
            CallableStatement sentencia = con.prepareCall("SELECT codigotipo from  tipo_unidad where nombretipo=?");
            sentencia.setString(1, nom);
            //rs=objConectar.consultarBD(strSQL);
            rs = sentencia.executeQuery(); 
            if (rs.next()) return rs.getInt("codigotipo");
        } catch (Exception e) {
            throw new Exception("Error al buscar tipo");
        }
        return 0;
    }
   
    
}
