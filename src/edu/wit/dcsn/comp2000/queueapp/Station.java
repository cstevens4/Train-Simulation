/*
 * Dave Rosenberg
 * Comp 2000 - Data Structures
 * Lab: Queue App: Train Simulation
 * Fall, 2019
 * 
 * Usage restrictions:
 * 
 * You may use this code for exploration, experimentation, and furthering your
 * learning for this course. You may not use this code for any other
 * assignments, in my course or elsewhere, without explicit permission, in
 * advance, from myself (and the instructor of any other course).
 * 
 * Further, you may not post or otherwise share this code with anyone other than
 * current students in my sections of this course. Violation of these usage
 * restrictions will be considered a violation of the Wentworth Institute of
 * Technology Academic Honesty Policy.
 *
 * Do not remove this notice.
 */

package edu.wit.dcsn.comp2000.queueapp;

import java.io.FileNotFoundException ;
import java.util.ArrayList;
import java.util.Arrays ;
import java.util.HashMap ;
import java.util.LinkedList ;
import java.util.Queue ;

/**
 * Representation of a station on a train route. A Station has two platforms
 * (queues) where Passengers wait before boarding a train. Passengers wait on
 * the platform which serves trains traveling in the direction that can take
 * them to their destination in the least time.
 * 
 * <p>
 * NOTE: This class is incomplete - you may want to restructure it based on your
 * implementation's requirements.
 * 
 * @author David M Rosenberg
 * @version 1.0.0	base version
 * 
 * @author Quinn Montgomery
 * @version 1.0.0
 * 
 */
public final class Station
	{
	// class-wide/shared information
	private static int		nextId =	1 ;	// enables automatic id assignment
	
	// per-instance fields
	private final int		id ;			// unique id for this station
	
	private final Location	location ;
	private HashMap< Direction, Queue< Passenger > >
							platforms ;

	
	/**
	 * @param onRoute the instance of the TrainRoute on which this Train operates
	 * @param positionOnRoute the specifications from the configuration file
	 */
	public Station( TrainRoute	onRoute,
					int			positionOnRoute )
    	{
    	id =							Station.nextId++ ;	// assign the next unique id
    	
    	// create a collection of platforms, determine the directions based on the route style,
    	// and create a pair of platforms indexable by the direction they service
    	platforms =						new HashMap<>() ;
    	
    	Direction oneDirection =		onRoute.getStyle() == RouteStyle.LINEAR
    										? Direction.OUTBOUND
    										: Direction.CLOCKWISE ;
    	platforms.put( oneDirection, new LinkedList<Passenger>() ) ;
    	platforms.put( oneDirection.reverse(), new LinkedList<Passenger>() ) ;
    	
    	// save the position along the route
    	location =						new Location( onRoute,
    	               					              positionOnRoute,
    	               					              Direction.STATIONARY ) ;
    	
    	}	// end 2-arg constructor
	
	
	/**
	 * Retrieves the location for this station
	 * 
	 * @return the location object for this station
	 */
	public Location getLocation()
    	{
    	return location ;
    	}	// end getLocation()
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
    	{
    	return String.format( "%s %,d", 
    	                      getClass().getSimpleName(),
    	                      id ) ;
    	}	// end toString()
	
	
	
	// TODO complete this
	
	public ArrayList<Passenger> embarkTrain(Train train){
		
		ArrayList<Passenger> newPass = new ArrayList<Passenger>();
		
		Direction direction = train.getLocation().getDirection();
		
		while(!platforms.isEmpty()&& newPass.size()+train.numOfPassengers() < train.getCapacity()) {
			
			newPass.add(platforms.get(direction).poll());
			
		}
		
		return newPass;
		
	}
	
	public void disembarkTrain(Train train) {
		
		train.disembarkTrain();
		
	}
	
	/**
	 * Unit test driver
	 * 
	 * @param args -unused-
	 * @throws FileNotFoundException see {@link Configuration#Configuration()}
	 */
	public static void main( String[] args ) throws FileNotFoundException
    	{
    	Configuration	theConfig =		new Configuration() ;
    	
    	TrainRoute		theRoute =		new TrainRoute( theConfig.getRoute() ) ;
    	int[]			theStationSpecs =	theConfig.getStations() ;
    	
    	System.out.printf( "Using configuration:%n\t%s%n",
    	                   Arrays.toString( theStationSpecs )
    	                   ) ;
    	
    	System.out.println( "The result is:" ) ;
    	
    	for( int stationPosition : theStationSpecs )
    		{
    		Station		aStation =		new Station( theRoute, stationPosition ) ;
    		System.out.printf( "\t%s is %s%n",
    		                   aStation,
    		                   aStation.getLocation()
    						) ;
    		}	// end foreach()
    	}	// end test driver main()

	}	// end class TrainRoute
