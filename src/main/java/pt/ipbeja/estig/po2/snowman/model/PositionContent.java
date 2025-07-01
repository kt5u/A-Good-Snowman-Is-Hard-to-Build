package pt.ipbeja.estig.po2.snowman.model;

/**
 * Represents the possible contents of a board position
 *
 * @author Denis Cicau 25442
 * @author Ângelo Teresa 25441
 */
public enum PositionContent {
    NO_SNOW,    // Empty ground without snow
    SNOW,       // Ground with snow
    BLOCK,      // Obstacle/block
    SNOWBALL,   // Single snowball
    SNOWMAN,    // Complete snowman (3 stacked snowballs)
    MONSTER     // The player character
}