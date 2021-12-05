package co.edu.uniandes.fourbidden.vinilos.vista


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import co.edu.uniandes.fourbidden.vinilos.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class PruebaCrearAlbum {

    @get:Rule
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun pruebaCrearAlbum() {
        val botonEntrar = onView(
            allOf(
                withId(R.id.bingreso), withText("Entrar"),
                isDisplayed()
            )
        )
        botonEntrar.perform(click())
        Thread.sleep(1000)

        val botonCrearAlbum = onView(
            allOf(
                withId(R.id.btCrearAlbum),
                isDisplayed()
            )
        )
        botonCrearAlbum.perform(click())
        Thread.sleep(1000)

        val nombre = onView(
            allOf(
                withId(R.id.form_album_name),
                isDisplayed()
            )
        )
        nombre.perform(typeText(getRandomString(20)), closeSoftKeyboard())
        Thread.sleep(500)
        nombre.check(matches(isDisplayed()))

        val cover = onView(
            allOf(
                withId(R.id.form_album_cover),
                isDisplayed()
            )
        )
        cover.perform(typeText(getRandomString(20)), closeSoftKeyboard())
        Thread.sleep(500)
        cover.check(matches(isDisplayed()))

        val fechaLanzamiento = onView(
            allOf(
                withId(R.id.form_album_date),
                isDisplayed()
            )
        )
        fechaLanzamiento.perform(typeText("2021-01-20"), closeSoftKeyboard())
        Thread.sleep(500)
        fechaLanzamiento.check(matches(isDisplayed()))


        val descripcion = onView(
            allOf(
                withId(R.id.form_album_descr),
                isDisplayed()
            )
        )
        descripcion.perform(typeText(getRandomString(20)), closeSoftKeyboard())
        Thread.sleep(500)
        descripcion.check(matches(isDisplayed()))

        val genero = onView(
            allOf(
                withId(R.id.form_album_genre),
                isDisplayed()
            )
        )
        genero.perform(typeText("Salsa"), closeSoftKeyboard())
        Thread.sleep(500)
        genero.check(matches(isDisplayed()))

        val disquera = onView(
            allOf(
                withId(R.id.form_album_recordlabel),
                isDisplayed()
            )
        )
        disquera.perform(typeText("EMI"),closeSoftKeyboard())
        Thread.sleep(500)
        disquera.check(matches(isDisplayed()))


        val clicBoton = onView(
            allOf(
                withId(R.id.create_album),
                isDisplayed()
            )
        )
        clicBoton.perform(click())
        Thread.sleep(5000)
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    fun getRandomString(length: Int) : String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }
}
