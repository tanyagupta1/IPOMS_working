package com.desiseducare.services;


import com.desiseducare.models.OpenIPO;
import com.desiseducare.repository.OpenIpoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * performs CRUD operations and some ipo date validation check
 */
@Service
public class OpenIPOService
{
    @Autowired
    OpenIpoRepository openIPORepository;

    /**
     * Adds the open IPO into the repository after checking start and end dates
     * @param openIPO an integer representing the OpenIPO id
     * @return an empty string in case of successful addition of IPO, or else returns a string with the error message
     */
    public String addOpenIPO(OpenIPO openIPO)
    {
        if(openIPO.getEndDate().before(openIPO.getStartDate()))
        {
            return "END date can't be before Start date";
        }
        if(openIPO.getCapPrice()>openIPO.getFloorPrice()*1.2)
        {
            return "Cap can't exceed floor price by more than 20%";
        }
        if(openIPO.getLotSize()<0)
        {
            return "Lot size must be a positive integer";
        }
        if(openIPO.getTotalNumberOfSharesOffered()<0)
        {
            return "Total number of shares must be a positive integer";
        }
        openIPORepository.save(openIPO);
        return "";
    }

    /**
     * finds all open IPOs
     * @return returns a list of open IPOs
     */
    public List<OpenIPO> getAllOpenIPOs()
    {
        return openIPORepository.findAll();
    }

    /**
     * finds a particular open IPO
     * @param id an integer representing openIPO id
     * @return the IPO object. could be null incase the IPO with that particular ID does not exist
     */
    public Optional<OpenIPO> getOpenIPO(Integer id)
    {
        return openIPORepository.findById(id);
    }

    /**
     * deletes a particular openIPO if it exists in the db
     * @param id id an integer representing openIPO id
     */
    public void deleteOpenIPO(Integer id)
    {
        if(openIPORepository.existsById(id))
        {
            openIPORepository.deleteById(id);
        }
    }
}
