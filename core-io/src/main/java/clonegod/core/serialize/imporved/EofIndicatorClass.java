package clonegod.core.serialize.imporved;

import java.io.Serializable;

/*
 * Class whose instance will be written at EOF during serialization
 * to indicate EOF during deSerialization process.
 */
class EofIndicatorClass implements Serializable{}