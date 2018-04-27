## wiki
	https://github.com/google/guava/wiki

## unit test
	https://github.com/google/guava/tree/master/guava-tests/test/com/google/common
	
--------------------------------

## 【Basic Guava Utilities】

	Joiner
	Splitter
	strings
		Strings
		Charsets
		CharMatcher
	Preconditions
	Object Utilities
		toString:	Objects.toStringHelper
		checkNull:	Objects.firstNonNull(someString,""default value"");
		hashCode:	Objects.hashCode(title, author, publisher, isbn);
		CompareTo:	ComparisonChain.start().xxx.result();


## 【Functional Programming】

	Function & Functions
	Predicate & Predicates
	Supplier & Suppliers


## 【Collections】

	FluentIterable
		FluentIterable.from(Iterable)
					.transform(Function)
					.filter(Predicate)
					.limit()
					.toList()/toSet()/toMap(Function)
	Lists
		Lists.newArrayList
		Lists.partition
	Sets
		Sets.newHashSet
		Sets.difference
		Sets.symmetricDifference
		Sets.intersection
		Sets.union
	Multiset
		HashMultiset
	Maps
		Maps.uniqueIndex
		Maps.asMap
		Transforming maps
	Multimaps
		ArrayListMultimap
		HashMultimap
	BiMap
		HashBiMap
		inverse: value -> key
	Table
		Map Of Map
		Table Views
	Range
		Range.closed
		Range.open
		Range Is A Predicate -> filter collection
	Immutable collections
		ImmutableList.of 		 / new ImmutableList.Builder
		ImmutableListMultimap.of / new ImmutableListMultimap.Builder
	Ordering
		Reverse sorting
		Accounting for null
		Secondary sorting
		minimum and maximum

## 【Concurrency】

	Monitor & Guard
	
	ListeningExecutorService
	ListenableFuture
	AsyncFunction
	Futures.transformAsync
	Futures.catchingAsync
	FutureCallback
	Futures.addCallback(ListenableFuture<String> future, 
						FutureCallback<? super String> callback, 
						Executor executor)
	
	RateLimiter
	
	
## 【Cache】

	MapMaker - ConcurrentMap
	
	CacheBuilder
		concurrencyLevel
		expire/refresh
		maximumSize/softValues/weakKeys/weakValues
		CacheStats
		RemovalListener
		CacheLoader
	
	LoadableCache
		get,getAll,refresh...
		
		
## 【EventBus】

	EventBus
		post
		register
		unregister
		handler method
			@Subscribe
	
	AysncEventBus
		handler method
			@Subscribe
			@AllowConcurrentEvents
			
	DeadEvents
	
	Dependency Injection - By SpringFramework
	
	
## 【Files】

	Files
		copy,move,reading, Writing
	Closer
	Source & Sink
		ByteSource, ByteSink,
		CharSource, CharSink
	ByteStreams,CharStreams
	BaseEncoding

## 【Odds and Ends】

	Hashing/HashFunction
	BloomFilter
	Optional
	Throwable
	

	
