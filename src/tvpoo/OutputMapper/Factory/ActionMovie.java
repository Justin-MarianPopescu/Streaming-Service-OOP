package tvpoo.OutputMapper.Factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import tvpoo.InputMapper.InputMapper;
import tvpoo.OutputMapper.Singleton.InstanceAction;
import tvpoo.OutputMapper.Singleton.OutputActions;

public interface ActionMovie {
    /**
     * Only the signature of the method
     * @param a output actions
     * @param objectMapper object that maps nodes into json file
     * @param inputMapper object that takes nodes from json file
     * @param moviesAction instance with methods for writing in result_basic
     * @param moviesListArrNode ArrayNode with all the movies from user
     */
    void actionMovie(OutputActions a,
                     InstanceAction moviesAction,
                     ObjectMapper objectMapper,
                     InputMapper inputMapper,
                     ArrayNode moviesListArrNode);
}
