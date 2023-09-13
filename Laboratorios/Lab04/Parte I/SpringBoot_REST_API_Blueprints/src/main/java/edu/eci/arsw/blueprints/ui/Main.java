package edu.eci.arsw.blueprints.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

/**
 * @author Angie Mojica
 * @author Daniel Santanilla
 */
public class Main {

    
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices bps = ac.getBean(BlueprintsServices.class);
        
        // Prueba 1
        Blueprint bp = new Blueprint("Angie", "plano1");
        try {
            bps.addNewBlueprint(bp);
            System.out.println(bps.getBlueprint("Angie", "plano1"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Prueba 2
        Blueprint bp2 = new Blueprint("Angie", "plano2");
        try {
            bps.addNewBlueprint(bp2);
            System.out.println(bps.getBlueprintsByAuthor("Angie"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Prueba 3
        Blueprint bp3 = new Blueprint("Daniel", "plano3");
        bp3.addPoint(new Point(1, 1));
        bp3.addPoint(new Point(2, 2));
        bp3.addPoint(new Point(3, 3));
        try {
            bps.addNewBlueprint(bp3);
            System.out.println(bps.getAllBlueprints());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        

    }
    
}
