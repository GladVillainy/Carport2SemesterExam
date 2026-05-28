package app;


import entities.*;
import persistence.ConnectionPool;
import persistence.MaterialMapper;

import java.util.ArrayList;

public class ListGenerator {

    public static TotalOrderLines ListGenerator(Carport carport, ConnectionPool connectionPool){
        ArrayList<OrderLine> orderLines = new ArrayList<>();

        Material raft = MaterialMapper.getMaterialByName("45x195 mm. spærtræ ubh.", connectionPool);
        Material beam = MaterialMapper.getMaterialByName("45x195 mm. spærtræ ubh.", connectionPool);
        Material pole = MaterialMapper.getMaterialByName("97x97 mm. trykimp. Stolpe", connectionPool);

        //raft
        raft.setLength((int) RaftGenerator.raftLength(carport.getLength()));
        orderLines.add(new OrderLine(RaftGenerator.raftGenerator(carport.getLength()), raft, raft.getLength()));

        //beam
        beam.setLength((int) BeamGenerator.beamLength(carport.getWidth()));
        orderLines.add(new OrderLine(BeamGenerator.beamGenerator(), beam, beam.getLength()));

        //pole
        pole.setLength((int) PoleGenerator.poleLength());
        orderLines.add(new OrderLine(PoleGenerator.poleGenerator(carport.getLength(), carport.isShed()), pole, pole.getLength()));

        //tilføjer det hele til totalorderlines
        TotalOrderLines totalOrderLines = new TotalOrderLines(orderLines);

        return totalOrderLines;
    }


}
