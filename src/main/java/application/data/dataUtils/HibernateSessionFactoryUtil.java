package application.data.dataUtils;


import application.data.persistance.entities.RecipeInstruction;
import application.data.persistance.entities.RecipeIngredient;
import application.data.persistance.entities.Recipe;
import application.data.persistance.entities.RecipeTag;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateSessionFactoryUtil {
	private static final Logger log = LogManager.getLogger(HibernateSessionFactoryUtil.class);
	private static final String CONFIG_FILE = "hibernate.cfg.xml";

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration()
						.configure(CONFIG_FILE)
						.addAnnotatedClass(Recipe.class)
						.addAnnotatedClass(RecipeInstruction.class)
						.addAnnotatedClass(RecipeIngredient.class)
						.addAnnotatedClass(RecipeTag.class);

				StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
				sessionFactory = configuration.buildSessionFactory(builder.build());
			} catch (Exception e) {
				log.error(e);
			}
		}
		return sessionFactory;
	}
}
