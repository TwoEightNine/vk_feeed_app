package global.msnthrp.feeed.base

/**
 * for processing back press in fragments
 */
interface BackPressable {

    /**
     * returns true if activity should call its super
     */
    fun pressBack(): Boolean
}