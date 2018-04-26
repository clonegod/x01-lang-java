package clonegod.framework.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clonegod.framework.dal.dao.Post;
import clonegod.framework.dal.dao.PostMapper;

@Service
public class PostService {

    @Autowired
    private PostMapper postMapper;

    public Post getPost(int id){
    	return postMapper.selectByPrimaryKey(id);
    }
}
