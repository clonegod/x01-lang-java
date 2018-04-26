全文搜索引擎
1. 索引	建立目录，快速定位
2. 分词 	如何将一句话拆分出多个单词，中文分词器有哪些？
3. 搜索	


Field.Store	存储选项
	YES		Field的内容会存入索引文件中
	NO		Field的内容不会存入索引文件中
	
Field.Index 索引选项
	ANALYZED					进行索引和分词，适用于标题、摘要
	ANALYZED_NO_NORMS			进行索引和分词，但是不存储norms信息，norms中包含了创建索引的时间和权值等信息
	NO							不索引
	NOT_ANALYZED				进行索引，但是不分词，如文件名、身份证号、姓名、ID等，使用于精确查找
	NOT_ANALYZED_NO_NORMS		进行索引，但不分词，也不存储norms信息
	
	

IndexReader 一般都设计为单利对象（打开和关系都比较耗费资源），reader一般不关闭
IndexWriter	使用完成就关闭，注意commit


RAMDirectory	速度快，索引全部存放在内存中
FSDirectory		速度慢，索引保存在文件系统中


