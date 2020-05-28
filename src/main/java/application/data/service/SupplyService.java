package application.data.service;

import application.data.model.Category;
import application.data.model.Supply;
import application.data.repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;

@Service
public class SupplyService {
    @Autowired
    private SupplyRepository supplyRepository;

    public Supply findOne(int supplyId){
        return supplyRepository.findOne(supplyId);
    }

    public List<Supply> getAll(){
        return supplyRepository.findAll();
    }

    public void addNewSupply(Supply supply){
        supplyRepository.save(supply);
    }

    @Transactional
    public void delSupply(int supplyId){
        supplyRepository.delete(supplyId);
    }

    public List<Supply> getListSupplyByName(String supplyName){
        return supplyRepository.getListSupplyByName(supplyName);
    }

}
