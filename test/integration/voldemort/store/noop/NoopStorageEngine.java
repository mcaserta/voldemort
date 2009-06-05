/*
 * Copyright 2009 Geir Magnusson Jr.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package voldemort.store.noop;

import voldemort.store.StorageEngine;
import voldemort.store.StoreCapabilityType;
import voldemort.store.NoSuchCapabilityException;
import voldemort.versioning.Versioned;
import voldemort.versioning.Version;
import voldemort.utils.Pair;
import voldemort.utils.ClosableIterator;
import voldemort.utils.ByteArray;
import voldemort.VoldemortException;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;


/**
 *  Implementation of a store that does the least amount possible.
 *
 */
public class NoopStorageEngine implements StorageEngine<ByteArray, byte[]> {

    protected String _name;
    protected ArrayList<Versioned<byte[]>> _data = new ArrayList<Versioned<byte[]>>(0);

    public NoopStorageEngine(String name) {
        _name = name;
    }

    public ClosableIterator<Pair<ByteArray, Versioned<byte[]>>> entries() {
        return null;
    }

    public List<Versioned<byte[]>> get(ByteArray key) throws VoldemortException {
        return _data;
    }

    public Map<ByteArray, List<Versioned<byte[]>>> getAll(Iterable<ByteArray> keys) throws VoldemortException {
        return null;
    }

    public void put(ByteArray key, Versioned<byte[]> value) throws VoldemortException {
    }

    public boolean delete(ByteArray key, Version version) throws VoldemortException {
        return true;
    }

    public String getName() {
        return _name;
    }

    public void close() throws VoldemortException {
    }

    public Object getCapability(StoreCapabilityType capability) {
        throw new NoSuchCapabilityException(capability, getName());
    }
}
