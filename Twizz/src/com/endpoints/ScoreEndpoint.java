package com.endpoints;

import com.endpoints.PMF;
import com.entities.ScoreEntity;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "scoreendpoint", namespace = @ApiNamespace(ownerDomain = "entities.com", ownerName = "entities.com", packagePath = ""))
public class ScoreEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listScore")
	public CollectionResponse<ScoreEntity> listScore(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<ScoreEntity> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(ScoreEntity.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<ScoreEntity>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (ScoreEntity obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<ScoreEntity> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getScore")
	public ScoreEntity getScore(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		ScoreEntity score = null;
		try {
			score = mgr.getObjectById(ScoreEntity.class, id);
		} finally {
			mgr.close();
		}
		return score;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param score the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertScore")
	public ScoreEntity insertScore(ScoreEntity score) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (containsScore(score)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(score);
		} finally {
			mgr.close();
		}
		return score;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param score the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateScore")
	public ScoreEntity updateScore(ScoreEntity score) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsScore(score)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(score);
		} finally {
			mgr.close();
		}
		return score;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeScore")
	public void removeScore(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			ScoreEndpoint score = mgr.getObjectById(ScoreEndpoint.class, id);
			mgr.deletePersistent(score);
		} finally {
			mgr.close();
		}
	}

	private boolean containsScore(ScoreEntity score) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(ScoreEntity.class, score.getId());
		} catch (javax.jdo.JDOObjectNotFoundException ex) {
			contains = false;
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
