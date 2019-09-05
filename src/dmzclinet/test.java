/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dmzclinet;

import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.developer.WSBindingProvider;
import static com.sun.xml.wss.saml.util.SAML20JAXBUtil.jaxbContext;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import org.iata.iata._2007._00.IATAPassengerConformanceIdentifyRQ;
import org.iata.iata._2007._00.IATAPassengerConformanceIdentifyRS;
import org.iata.iata._2007._00.PNRType;
import org.iata.iata._2007._00.POSType;
import org.iata.iata._2007._00.PersonNameType;
import org.iata.iata._2007._00.SourceType;
import org.iata.iata._2007._00.SourceType.RequestorID;

/**
 *
 * @author 642241
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException, DatatypeConfigurationException {
        // TODO code application logic here
         String BP_OperatingCarrier = "CI";
        String BP_FlightNumber = "0928";
        String BP_departureDate = "20190930";
        String BP_DepartureAirport = "HKG";
        String BP_ArrivalAirport = "TPE";
        String BP_Surname = "CISBD";
        String BP_GivenName = "PRIORITY F";
        
        String BP_PNR = "KLVN5R";
        String BP_SeatNumber = "02A";
        BigDecimal Version = new BigDecimal("15.1");
        String transactionIdentifier = "12345678";
        String CorrelationID = "2333456";
        BigInteger SequenceNmbr = new BigInteger("1");     
        String RequestID = "";

        
        
        com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump = true;  //for debug
    
        
        
       
        
          IATAPassengerConformanceIdentifyRS PassengerIdentifyRS  =    new  IATAPassengerConformanceIdentifyRS();
       IATAPassengerConformanceIdentifyRQ PassengerIdentifyRQ  =    new  IATAPassengerConformanceIdentifyRQ();
       
        PassengerIdentifyRQ.setVersion(Version);
        PassengerIdentifyRQ.setTransactionIdentifier(transactionIdentifier);
        PassengerIdentifyRQ.setCorrelationID(CorrelationID);
        PassengerIdentifyRQ.setSequenceNmbr(SequenceNmbr);
        SourceType SourceType = new SourceType();
      POSType originator =   new POSType();
       
        RequestorID RequestorID = new RequestorID();
        RequestorID.setID("HKGSDB01");
         SourceType.setRequestorID(RequestorID);
         originator.getSource().add(SourceType);
        PassengerIdentifyRQ.setOriginator(originator);
        
       org.iata.iata._2007._00.IATAPassengerConformanceIdentifyRQ.Passenger pax = new org.iata.iata._2007._00.IATAPassengerConformanceIdentifyRQ.Passenger();
       PersonNameType name = new PersonNameType();
       IATAPassengerConformanceIdentifyRQ.Passenger.Segment mySegment = new IATAPassengerConformanceIdentifyRQ.Passenger.Segment();
       IATAPassengerConformanceIdentifyRQ.Passenger.Segment.Flight myFlight = new IATAPassengerConformanceIdentifyRQ.Passenger.Segment.Flight();
       IATAPassengerConformanceIdentifyRQ.Passenger.Segment.DepartureAirport DepartureAirport = new IATAPassengerConformanceIdentifyRQ.Passenger.Segment.DepartureAirport();
       IATAPassengerConformanceIdentifyRQ.Passenger.Segment.ArrivalAirport ArrivalAirport = new IATAPassengerConformanceIdentifyRQ.Passenger.Segment.ArrivalAirport();
       DateFormat format = new SimpleDateFormat("yyyyMMdd");
       GregorianCalendar gc = new GregorianCalendar();
       PNRType pnrType =  new PNRType();
      
       Date date = format.parse(BP_departureDate);
       gc.setTime(date);
       XMLGregorianCalendar departure_date = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
       System.out.println("departure_date:"+departure_date);
       name.setSurname(BP_Surname);
       name.getGivenName().add(BP_GivenName);
       pax.setName(name);
       PassengerIdentifyRQ.setPassenger(pax);
       
       myFlight.setFlightNumber(BP_FlightNumber);
       myFlight.setOperatingCarrier(BP_OperatingCarrier);
       myFlight.setScheduledDateOfDeparture(departure_date);
       mySegment.setFlight(myFlight);
       DepartureAirport.setAirportCode(BP_DepartureAirport);
       DepartureAirport.setSourceIndicator("L");
       ArrivalAirport.setAirportCode(BP_ArrivalAirport);
       ArrivalAirport.setSourceIndicator("X");
       pnrType.setID(BP_PNR);
       mySegment.setPNR(pnrType);
       mySegment.setArrivalAirport(ArrivalAirport);
       mySegment.setDepartureAirport(DepartureAirport);
       mySegment.setSeatNumber(BP_SeatNumber);
       PassengerIdentifyRQ.getPassenger().getSegment().add(mySegment);
  
        // ***** IdentifyPassengerByBoardPass ***** //
  identify(PassengerIdentifyRQ);
    }

    private static IATAPassengerConformanceIdentifyRS identify(org.iata.iata._2007._00.IATAPassengerConformanceIdentifyRQ msg) {
        aero.iata_aci.services.passenger.conformance.authentication.PassengerConformanceAuthenticationService service = new aero.iata_aci.services.passenger.conformance.authentication.PassengerConformanceAuthenticationService();
        aero.iata_aci.services.passenger.conformance.authentication.PassengerConformanceAuthenticationPortType port = service.getPassengerConformanceAuthenticationPort();
        BindingProvider prov = (BindingProvider) port;
        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("username", Collections.singletonList("HKGSBD"));
        headers.put("password", Collections.singletonList("CI@Sbd71"));
        prov.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        return port.identify(msg);
    }
    
}
