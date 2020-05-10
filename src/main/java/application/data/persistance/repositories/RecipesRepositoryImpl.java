package application.data.persistance.repositories;


import application.data.persistance.entities.Recipe;
import application.data.dataUtils.HibernateSessionFactoryUtil;
import application.data.persistance.repositories.interfaces.RecipesRepository;
import application.utils.QueryParamsParser;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class RecipesRepositoryImpl implements RecipesRepository {

	@Override
	public Recipe findByID(Long id) {
		return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Recipe.class, id);
	}

	@Override
	public List<Recipe> findByTitle(String title) {
		final String sql = "from Recipe where title = :queryParam";

		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Query query = session.createQuery(sql)
				.setParameter("queryParam", title);

		return query.getResultList();
	}

	@Override
	public List<Recipe> findByTitleLikeWithRecordLimit(String value, int recordLimit) {
		final String sql = "from Recipe where title like :queryParam";
		String keyWords = QueryParamsParser.getPreparedParamsForDBRequestLike(value);

		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Query query = session.createQuery(sql)
				.setParameter("queryParam", keyWords)
				.setMaxResults(recordLimit);

		return query.getResultList();
	}

	@Override
	public void save(Recipe recipe) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.save(recipe);
		transaction.commit();
		session.close();
	}

	@Override
	public void update(Recipe recipe) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.update(recipe);
		transaction.commit();
		session.close();
	}

	@Override
	public void delete(Recipe recipe) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(recipe);
		transaction.commit();
		session.close();
	}
}
