package ru.nsu.ccfit.pm.econ.view.shared.localization;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.Locale;

import javax.annotation.Nullable;

import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Theme;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Provides localization capabilities for both client and server.
 * <p>Localization resources are shared between client and server.
 * Localization files should be named like: 
 * <ul>
 * <li><tt>'Localization.json'</tt> - for default locale</li>
 * <li><tt>'Localization_ru.json'</tt> - for russian locale</li>
 * </ul>
 * </p>
 * <p>Localization resource files should include <tt>alphabet</tt>
 * property containing all symbols that should be displayed in this
 * locale. This property is used to select appropriate font for the
 * locale.</p>
 * @author dragonfly
 */
public class Localization {
	
	private static final String SAMPLE_RESOURCE = "alphabet";
	
	@Nullable 
	private String language = null;
	
	private Resources resources;
	private Locale locale;
	
	public Localization(@Nullable String language) throws SerializationException, IOException {
		this.language = language;
		this.resources = initResources();
	}
	
	public Resources getResources() {
		return resources;
	}
	
	/**
	 * Adds resources for given class. All loaded resources will be available via 
	 * {@link #getResources()}. 
	 * <p>Resource file naming convention is same as one described in this class
	 * description.</p>
	 * @param Clazz Class to load resources for.
	 * @throws SerializationException
	 * @throws IOException
	 */
	public void addResourcesForClass(Class<?> Clazz) throws SerializationException, IOException {
		Resources res = new Resources(resources, Clazz.getName(), locale);
		resources = res;
	}
	
	private Resources initResources() throws SerializationException, IOException {
        locale = (language == null) ? Locale.getDefault() : new Locale(language);
        Resources resources = new Resources(getClass().getName(), locale, "UTF-8");

        Theme theme = Theme.getTheme();
        Font font = theme.getFont();

        // Search for a font that can support the sample string
        String sampleResource = resources.getString(SAMPLE_RESOURCE);
        checkNotNull(sampleResource, 
        		"Localization resource files should contain '{}' property", SAMPLE_RESOURCE);
        
        if (font.canDisplayUpTo(sampleResource) != -1) {
            Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();

            for (int i = 0; i < fonts.length; i++) {
                if (fonts[i].canDisplayUpTo(sampleResource) == -1) {
                    theme.setFont(fonts[i].deriveFont(Font.PLAIN, 12));
                    break;
                }
            }
        }
        
        return resources;
	}

}
