/**
 * Demonstrations of several implementations of the "singleton" pattern in which
 * separate "factory" objects are responsible for the "creational" aspects of
 * the singleton objects (i.e., ensuring only one instance is created).
 * <p />
 * I prefer to separate out singleton's "lifecycle" concerns into a second
 * class, in order to facilitate testing and to achieve better overall
 * "separation of concerns."
 * 
 * @author mross
 */
package mdr.ap.patterns.singletonfactory;