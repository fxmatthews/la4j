/*
 * Copyright 2011-2013, by Vladimir Kostyukov and Contributors.
 * 
 * This file is part of la4j project (http://la4j.org)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributor(s): -
 * 
 */

package org.la4j.decomposition;

import org.la4j.factory.Factory;
import org.la4j.matrix.Matrix;

public class CholeskyDecompositor implements MatrixDecompositor {

    @Override
    public Matrix[] decompose(Matrix matrix, Factory factory) {

        // TOOD: add check symmetric & positive defined

        if (matrix.rows() != matrix.columns()) {
            throw new IllegalArgumentException("Wrong matrix size:");
        }

        Matrix u = factory.createMatrix(matrix.rows(), matrix.rows());

        for (int j = 0; j < u.rows(); j++) {

            double d = 0.0;

            for (int k = 0; k < j; k++) {

                double s = 0.0;

                for (int i = 0; i < k; i++) {
                    s += u.get(k, i) * u.get(j, i);
                }

                s = (matrix.get(j, k) - s) / u.get(k, k);

                u.set(j, k, s);

                d = d + s * s;
            }

            d = matrix.get(j, j) - d;

            u.set(j, j, Math.sqrt(Math.max(d, 0.0)));

            for (int k = j + 1; k < u.rows(); k++) {
                u.set(j, k, 0.0);
            }
        }

        return new Matrix[] { u };
    }
}