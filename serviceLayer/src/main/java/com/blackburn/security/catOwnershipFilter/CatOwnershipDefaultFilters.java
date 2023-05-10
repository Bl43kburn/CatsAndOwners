package com.blackburn.security.catOwnershipFilter;

import com.blackburn.model.Cat;
import com.blackburn.security.FilterChain;

public class CatOwnershipDefaultFilters {

    public static FilterChain<Cat> getDefaultFilter() {
        return new CatOwnershipAuthorisationFilter().setNext(new CatOwnershipCheckOwner().setNext(new CatOwnershipCheckIfRoot()));
    }
}
