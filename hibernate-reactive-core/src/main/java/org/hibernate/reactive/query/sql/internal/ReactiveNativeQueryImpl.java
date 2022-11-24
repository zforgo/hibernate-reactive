/* Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.reactive.query.sql.internal;

import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.NotYetImplementedFor6Exception;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.hibernate.metamodel.model.domain.BasicDomainType;
import org.hibernate.query.BindableType;
import org.hibernate.query.QueryParameter;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;
import org.hibernate.query.spi.AbstractSelectionQuery;
import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.hibernate.reactive.logging.impl.Log;
import org.hibernate.reactive.logging.impl.LoggerFactory;
import org.hibernate.reactive.query.sql.spi.ReactiveNativeQueryImplementor;
import org.hibernate.type.BasicTypeReference;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Parameter;
import jakarta.persistence.TemporalType;
import jakarta.persistence.metamodel.SingularAttribute;

public class ReactiveNativeQueryImpl<R> extends NativeQueryImpl<R> implements ReactiveNativeQueryImplementor<R> {

	private static final Log LOG = LoggerFactory.make( Log.class, MethodHandles.lookup() );

	public ReactiveNativeQueryImpl(
			String memento,
			SharedSessionContractImplementor session) {
		super( memento, session );
	}

	@Override
	public int executeUpdate() throws HibernateException {
		throw LOG.nonReactiveMethodCall( "executeReactiveUpdate" );
	}

	@Override
	public CompletionStage<Integer> executeReactiveUpdate() {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public R getSingleResult() {
		throw LOG.nonReactiveMethodCall( "reactiveUniqueResult" );
	}

	@Override
	public CompletionStage<R> getReactiveSingleResult() {
		return reactiveList()
				.thenApply( this::reactiveSingleResult )
				.exceptionally( this::convertException );
	}

	private R reactiveSingleResult(List<R> list) {
		if ( list.isEmpty() ) {
			throw new NoResultException( String.format( "No result found for query [%s]", getQueryString() ) );
		}
		return uniqueElement( list );
	}


	@Override
	public R getSingleResultOrNull() {
		throw LOG.nonReactiveMethodCall( "getReactiveSingleResultOrNull" );
	}

	@Override
	public CompletionStage<R> getReactiveSingleResultOrNull() {
		return reactiveList()
				.thenApply( AbstractSelectionQuery::uniqueElement )
				.exceptionally( this::convertException );
	}

	private R convertException(Throwable t) {
		if ( t instanceof HibernateException ) {
			throw getSession().getExceptionConverter().convert( (HibernateException) t, getLockOptions() );
		}
		throw getSession().getExceptionConverter().convert( (RuntimeException) t, getLockOptions() );
	}

	@Override
	public CompletionStage<Optional<R>> reactiveUniqueResultOptional() {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public CompletionStage<R> reactiveUnique() {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public List<R> list() {
		throw LOG.nonReactiveMethodCall( "reactiveList" );
	}

	@Override
	public CompletionStage<List<R>> reactiveList() {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public R uniqueResult() {
		throw LOG.nonReactiveMethodCall( "executeReactiveUpdate" );
	}

	@Override
	public ReactiveNativeQueryImpl<R> applyGraph(RootGraph graph, GraphSemantic semantic) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> applyFetchGraph(RootGraph graph) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> addScalar(String columnAlias) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> addScalar(
			String columnAlias,
			@SuppressWarnings("rawtypes") BasicDomainType type) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> addScalar(String columnAlias, @SuppressWarnings("rawtypes") Class javaType) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> addScalar(String columnAlias, BasicTypeReference type) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <C> ReactiveNativeQueryImpl<R> addScalar(
			String columnAlias,
			Class<C> relationalJavaType,
			AttributeConverter<?, C> converter) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <O, J> ReactiveNativeQueryImpl<R> addScalar(
			String columnAlias,
			Class<O> domainJavaType,
			Class<J> jdbcJavaType,
			AttributeConverter<O, J> converter) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <C> ReactiveNativeQueryImpl<R> addScalar(
			String columnAlias,
			Class<C> relationalJavaType,
			Class<? extends AttributeConverter<?, C>> converter) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <O, J> ReactiveNativeQueryImpl<R> addScalar(
			String columnAlias,
			Class<O> domainJavaType,
			Class<J> jdbcJavaType,
			Class<? extends AttributeConverter<O, J>> converter) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> addAttributeResult(
			String columnAlias,
			@SuppressWarnings("rawtypes") Class entityJavaType,
			String attributePath) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> addAttributeResult(String columnAlias, String entityName, String attributePath) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> addAttributeResult(
			String columnAlias,
			@SuppressWarnings("rawtypes") SingularAttribute attribute) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> addEntity(String entityName) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> addEntity(String tableAlias, String entityName) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> addEntity(String tableAlias, String entityName, LockMode lockMode) {
		throw new NotYetImplementedFor6Exception();
	}
	@Override
	public ReactiveNativeQueryImpl<R> addEntity(@SuppressWarnings("rawtypes") Class entityType) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> addEntity(String tableAlias, @SuppressWarnings("rawtypes") Class entityType) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> addEntity(
			String tableAlias,
			@SuppressWarnings("rawtypes") Class entityClass,
			LockMode lockMode) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> addJoin(String tableAlias, String path) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> addJoin(
			String tableAlias,
			String ownerTableAlias,
			String joinPropertyName) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> addJoin(String tableAlias, String path, LockMode lockMode) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> addSynchronizedQuerySpace(String querySpace) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> addSynchronizedEntityName(String entityName) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> addSynchronizedEntityClass(@SuppressWarnings("rawtypes") Class entityClass) {
		throw new NotYetImplementedFor6Exception();
	}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// covariant overrides - Query / QueryImplementor


	@Override
	public ReactiveNativeQueryImpl<R> applyLoadGraph(RootGraph graph) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setAliasSpecificLockMode(String alias, LockMode lockMode) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setHint(String hintName, Object value) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setHibernateFlushMode(FlushMode flushMode) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setFlushMode(FlushModeType flushMode) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setFollowOnLocking(boolean enable) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setCacheMode(CacheMode cacheMode) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setCacheable(boolean cacheable) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setCacheRegion(String cacheRegion) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setCacheRetrieveMode(CacheRetrieveMode cacheRetrieveMode) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setCacheStoreMode(CacheStoreMode cacheStoreMode) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setTimeout(int timeout) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setFetchSize(int fetchSize) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setReadOnly(boolean readOnly) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setLockOptions(LockOptions lockOptions) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setHibernateLockMode(LockMode lockMode) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setLockMode(LockModeType lockMode) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setLockMode(String alias, LockMode lockMode) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setComment(String comment) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setMaxResults(int maxResult) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setFirstResult(int startPosition) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> addQueryHint(String hint) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <T> ReactiveNativeQueryImpl<T> setTupleTransformer(TupleTransformer<T> transformer) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setResultListTransformer(ResultListTransformer<R> transformer) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setParameter(String name, Object val) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameter(String name, P val, BindableType<P> type) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameter(String name, P val, Class<P> type) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setParameter(String name, Instant value, TemporalType temporalType) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setParameter(String name, Date value, TemporalType temporalType) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setParameter(String name, Calendar value, TemporalType temporalType) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setParameter(int position, Object val) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameter(int position, P val, Class<P> type) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameter(int position, P val, BindableType<P> type) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setParameter(int position, Instant value, TemporalType temporalType) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setParameter(int position, Date value, TemporalType temporalType) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setParameter(int position, Calendar value, TemporalType temporalType) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameter(QueryParameter<P> parameter, P val) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameter(QueryParameter<P> parameter, P val, Class<P> type) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameter(
			QueryParameter<P> parameter,
			P val,
			BindableType<P> type) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameter(Parameter<P> param, P value) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setParameter(Parameter<Date> param, Date value, TemporalType temporalType) {
		throw new NotYetImplementedFor6Exception();
	 }

	@Override
	public ReactiveNativeQueryImpl<R> setParameter(
			Parameter<Calendar> param,
			Calendar value,
			TemporalType temporalType) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setParameterList(String name, @SuppressWarnings("rawtypes") Collection values) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameterList(
			String name,
			Collection<? extends P> values,
			Class<P> type) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameterList(
			String name,
			Collection<? extends P> values,
			BindableType<P> type) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setParameterList(String name, Object[] values) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameterList(String name, P[] values, Class<P> type) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameterList(String name, P[] values, BindableType<P> type) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setParameterList(int position, @SuppressWarnings("rawtypes") Collection values) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameterList(
			int position,
			Collection<? extends P> values,
			Class<P> type) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameterList(
			int position,
			Collection<? extends P> values,
			BindableType<P> type) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setParameterList(int position, Object[] values) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameterList(int position, P[] values, Class<P> javaType) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameterList(int position, P[] values, BindableType<P> type) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameterList(
			QueryParameter<P> parameter,
			Collection<? extends P> values) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameterList(
			QueryParameter<P> parameter,
			Collection<? extends P> values,
			Class<P> javaType) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameterList(
			QueryParameter<P> parameter,
			Collection<? extends P> values,
			BindableType<P> type) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameterList(QueryParameter<P> parameter, P[] values) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameterList(
			QueryParameter<P> parameter,
			P[] values,
			Class<P> javaType) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public <P> ReactiveNativeQueryImpl<R> setParameterList(
			QueryParameter<P> parameter,
			P[] values,
			BindableType<P> type) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setProperties(Object bean) {
		throw new NotYetImplementedFor6Exception();
	}

	@Override
	public ReactiveNativeQueryImpl<R> setProperties(@SuppressWarnings("rawtypes") Map bean) {
		throw new NotYetImplementedFor6Exception();
	}
}
