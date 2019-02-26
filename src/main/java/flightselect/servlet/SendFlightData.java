package flightselect.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import flightselect.constant.Compagny;
import flightselect.flightdata.FlightData;
import flightselect.flightdata.FlightDataReader;
import flightselect.flightdata.FlightDataWriter;
import flightselect.sortData.SortFlightData;
/**
 * Servlet implementation class SimpleServlet
 */
@WebServlet("/api/flights")
public class SendFlightData extends HttpServlet {
   private static final long serialVersionUID = 1L;
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
         throws ServletException, IOException {
      resp.setContentType("application/json");
      
      FlightDataReader jazz = new FlightDataReader(Compagny.JAZZ_AIR);
      List<FlightData> jazzData = jazz.getFlightData();
      FlightDataReader moon = new FlightDataReader(Compagny.MOON_AIR);
      List<FlightData> moonData = moon.getFlightData();
      FlightDataReader beam = new FlightDataReader(Compagny.BEAM_AIR);
      List<FlightData> beamData = beam.getFlightData();
      
      //on merge les 3 listes et on tri dans la fouler avec un outil dédié
      String result = FlightDataWriter.getJson(SortFlightData.sortData(jazzData, moonData, beamData));
      
      resp.getWriter().write(result);
   }
}